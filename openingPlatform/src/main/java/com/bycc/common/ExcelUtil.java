package com.bycc.common;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.smartframework.platform.exception.BusinessException;
import org.smartframework.utils.helper.DateHelper;
import org.smartframework.utils.helper.StringHelper;



/**
 * 功能名：Excel解析工具.
 * <p/>
 * 类描述：工具类，根据传入的Exccel文件和Class对文件进行解析并返回对象集合
 *
 * @author 于明哲
 * @since 2015/02/03
 */
public final class ExcelUtil {
	/**
	 * 私有构造函数.
	 * <p>
	 * 防止创建此工具类的实例
	 */
	private ExcelUtil() {}

	/**
	 * 将数据类型名称与其包装类进行映射，便于后期解析、赋值.
	 */
	private static final Map<String, Class<?>> TYPE = new HashMap<String, Class<?>>();

	/**
	 * 默认读取缓冲区大小为1MB字节.
	 */
	private static final int BUFFER_SIZE = 1000000;

	// 初始化
	static {
		TYPE.put("int", Integer.class);
		TYPE.put("Integer", Integer.class);
		TYPE.put("float", Float.class);
		TYPE.put("Float", Float.class);
		TYPE.put("long", Long.class);
		TYPE.put("Long", Long.class);
		TYPE.put("double", Double.class);
		TYPE.put("Double", Double.class);
		TYPE.put("boolean", Boolean.class);
		TYPE.put("Boolean", Boolean.class);
		TYPE.put("BigDecimal", BigDecimal.class);
		TYPE.put("String", String.class);
		TYPE.put("Date", Date.class);
	}

	/**
	 * 解析文件.
	 * <p>
	 * 根据上传的excel文件, 将每一行数据解析为相应的对象并返回这些对象的集合
	 *
	 * @param <D> 目标类型
	 * @param inputStream 上传的excel文件流
	 * @param clz 要转换成的目标类型
	 * @throws BusinessException 解析失败抛出业务异常
	 * @return 解析后的对象集合
	 */
	public static <D> List<D> parse(final InputStream inputStream, final Class<D> clz) throws BusinessException {
		Workbook workbook = checkFileType(inputStream);
		// 用于存放最终解析结果的集合
		List<D> list = new ArrayList<D>();
		if (workbook != null) {
			// 遍历workbook, 获取每一个sheet进行解析
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				Sheet sheet = workbook.getSheetAt(i);
				// 将每个sheet解析出来的集合都添加到结果集合中
				list.addAll(convertSheetToList(sheet, clz));
			}
		}
		return list;
	}

	/**
	 * 检查文件是否是Excel文件.
	 *
	 * @param inputStream 上传的excel文件流
	 * @return 文件检查通过后, 将文件读入到Workbook对象中并返回workbook
	 * @throws BusinessException 文件类型不正确抛出业务异常
	 */
	private static Workbook checkFileType(final InputStream inputStream) throws BusinessException {
		if (inputStream != null) {
			Workbook workbook = null;
			// 获取文件流
			try {
				// 从流中创建Workbook
				BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, BUFFER_SIZE);
				workbook = WorkbookFactory.create(bufferedInputStream);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				// 文件类型不正确时抛出异常
				throw new BusinessException("文件不是excel文档");
			} catch (NoClassDefFoundError e) {
				e.printStackTrace();
				// 文件验证过程出错
				throw new BusinessException("解析出错: 文档为空或文件不是excel文档!");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				// 文件未找到异常
				throw new BusinessException("文件未找到!");
			} catch (IOException e) {
				e.printStackTrace();
				throw new BusinessException("IO异常!");
			} catch (InvalidFormatException e) {
				e.printStackTrace();
				// 文件类型不正确时抛出异常
				throw new BusinessException("文件不是excel文档");
			} catch (Exception e) {
				e.printStackTrace();
				if (e.getMessage().equalsIgnoreCase("Unexpected missing row when some rows already present")) {
					throw new BusinessException("文件格式不正确，请重新保存后再上传！");
				}
				throw new BusinessException("文件格式不正确！");
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return workbook;
		} else {
			throw new BusinessException("文件不存在");
		}
	}

	/**
	 * <b>内部方法</b>, 用于校验excel文件的标题行(第2行)
	 * <p>
	 * 当实体类中@ExcelColumn标记的所有字段都出现在标题行(第2行)中时视为通过校验, 否则抛出异常
	 *
	 * @param excelHeaders excel文件中的(第2行)所有列名
	 * @param columnMapping 实体类中所有用@ExcelColumn标记的字段与注解的映射
	 * @throws BusinessException 标题行校验失败抛出业务异常
	 * @author 于明哲
	 * @date Feb 10, 2015 9:11:31 AM
	 */
	private static void validateExcelHeader(Set<String> excelHeaders, Map<Field, ExcelColumn> columnMapping) throws BusinessException {
		// 记录无需读取的列
		List<Field> deleteFields = null;
		for (Field field : columnMapping.keySet()) {
			ExcelColumn column = columnMapping.get(field);
			if (!excelHeaders.contains(column.title())) {
				// 如果在excel中没有字段, 再判断该字段是否强制要求出现在excel中

				if (field.getAnnotation(ExcelColumn.class).required()) {
					throw new BusinessException("Excel标题行校验失败, 找不到列【" + column.title() + "】, 请确保按照模板填写, 不要随意修改或删减列!");
				} else {
					if (deleteFields == null) {
						deleteFields = new ArrayList<Field>();
					}
					// 如果该字段不强制出现在excel中, 则将该字段加入到删除队列中, 后续就不在excel中读取数据
					// 如果立即删除会抛出并发修改异常
					deleteFields.add(field);
				}
			}
		}
		// 判空
		if (deleteFields == null) {
			return;
		}
		// 删除无需读取的列
		for (Field field : deleteFields) {
			columnMapping.remove(field);
		}
	}

	/**
	 * 判断是否是空行
	 * <p>
	 * 如果行内每个单元格都为空,则视为空行;否则视为数据行
	 *
	 * @param row 代表excel文件中的每一行
	 * @return 若为空行则返回true, 否则只要有一个单元格不为空就返回false
	 * @author yumingzhe
	 * @date 2015年3月19日
	 *
	 */
	private static boolean isRowBlank(Row row) {
		for (int i = 0; i < row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i);
			if (cell != null) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String cellValue = cell.getStringCellValue();
				if (cellValue != null && !"".equals(cellValue)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 是否是时间格式
	 *
	 * @param str
	 * @return
	 */
	private static String isDateFormat(String str) {
		if (StringHelper.isAllEmpty(str)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("yyyy-MM-dd", "([0-9]{4})-([0-9]{1,2})-([0-9]{1,2})");
		map.put("yyyy/MM/dd", "([0-9]{4})/([0-9]{1,2})/([0-9]{1,2})");
		map.put("yyyy.MM.dd", "([0-9]{4}).([0-9]{1,2}).([0-9]{1,2})");
		for (String key : map.keySet()) {
			if (str.matches(map.get(key))) {
				return key;
			}
		}
		return null;
	}

	/**
	 * 将sheet中的每行数据转换成对象并返回这些对象的集合.
	 *
	 * @param <D> 泛型
	 * @param sheet 要解析的工作表对象
	 * @param clazz 目标类型
	 * @return 解析后的对象的集合
	 *
	 * @throws BusinessException 解析失败抛出业务异常
	 */
	private static <D> List<D> convertSheetToList(final Sheet sheet, final Class<D> clazz) throws BusinessException {
		if (sheet.getPhysicalNumberOfRows() <= 0) {
			return new ArrayList<D>();
		}
		// 读取第二行获取所有的列名
		Row firstRow = sheet.getRow(1);
		if (firstRow == null) {
			return new ArrayList<D>();
		}
		// 缓存列号与字段名的对应关系
		Map<Integer, Object[]> mapping = new HashMap<Integer, Object[]>();
		// 获取所有字段
		Field[] fields = clazz.getDeclaredFields();
		Map<Field, ExcelColumn> columnMapping = new HashMap<Field, ExcelColumn>();
		// 获取添加注解的字段
		for (Field field : fields) {
			ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
			if (annotation != null) {
				// 将filed与注解进行映射
				columnMapping.put(field, annotation);
			}
		}

		// 存放excel列名
		Set<String> columnTitles = new HashSet<String>();
		for (int i = 0; i < firstRow.getLastCellNum(); i++) {
			Cell cell = firstRow.getCell(i);
			if (cell != null) {
				columnTitles.add(cell.getStringCellValue().trim());
			}
		}

		// 校验标题列
		validateExcelHeader(columnTitles, columnMapping);

		// 列号与字段进行映射
		for (int i = 0; i < firstRow.getLastCellNum(); i++) {
			Cell cell = firstRow.getCell(i);
			if (cell != null) {
				String columnName = cell.getStringCellValue();
				// 判断每个列名是否都是对象的字段名
				for (Field field : columnMapping.keySet()) {
					if (columnMapping.get(field).title().equalsIgnoreCase(columnName)) {
						// 如果该列名是字段名,则将列号与字段元数据进行映射
						mapping.put(Integer.valueOf(i), new Object[] {field, field.getType(),
								columnMapping.get(field)});
						break;
					}
				}
			}
		}

		// 用于存放最终结果的集合
		List<D> result = new ArrayList<D>();
		// 读取剩余行进行转换
		int currentColumn = 0;
		for (int i = 2; i <= sheet.getLastRowNum() + 1; i++) {
			try {
				// 创建对象
				D obj = null;
				Row row = sheet.getRow(i);
				if (row != null && !isRowBlank(row)) {// 如果是空行则忽略该行
					for (Integer j : mapping.keySet()) {
						currentColumn = j;
						Object cellValue = null;
						Class<?> fieldType = (Class<?>) mapping.get(j)[1];

						//字段对应的注解
						ExcelColumn excelColumn = (ExcelColumn) mapping.get(j)[2];
						// 字段属于字符串类型
						if (fieldType == String.class) {
							try {
								row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
								cellValue = row.getCell(j).getStringCellValue();
								if (cellValue.equals("") && !excelColumn.allowBlank()) {
									throw new BusinessException("第" + (i + 1) + "行【" + excelColumn.title() + "】列不允许为空");
								}

								if (excelColumn.dateType() && cellValue != null
										&& !StringHelper.isAllEmpty(cellValue.toString())) {
									// 对日期格式进行校验
									String format = isDateFormat(cellValue.toString());
									if (StringHelper.isAllEmpty(format)) {
										throw new BusinessException("第" + (i + 1) + "行【" + excelColumn.title()
												+ "】格式不正确，支持格式为：yyyy-MM-dd、yyyy/MM/dd、yyyy.MM.dd");
									}
									try {
										Date date = DateHelper.formatStringToDate(cellValue.toString(), format);
										cellValue = DateHelper.formatDateToString(date, excelColumn.impDateFormat());
									} catch (ParseException e) {
										e.printStackTrace();
										throw new BusinessException("第" + (i + 1) + "行【" + excelColumn.title()
												+ "】日期格式转换错误");
									}

								}
								// 延迟初始化对象
								if (obj == null) {
									obj = clazz.newInstance();
								}
							} catch (NullPointerException e) {
								if (!excelColumn.allowBlank()) {
									throw new BusinessException("第" + (i + 1) + "行【" + excelColumn.title() + "】列不允许为空");
								}
								continue;
							}
						}
						// 字段属于日期类型
						else if (fieldType == Date.class) {
							try {
								// 获取cell的类型,防止读取错误因为有的cell是numeric类型的,有的是string类型的
								int celltype = row.getCell(j).getCellType();
								if (celltype == Cell.CELL_TYPE_NUMERIC) {
									//判断是否是日期格式
									boolean isDateCell = HSSFDateUtil.isCellDateFormatted(row.getCell(j));
									if (!isDateCell) {
										throw new ParseException(null, j);
									}
									cellValue = row.getCell(j).getDateCellValue();
								} else if (celltype == Cell.CELL_TYPE_STRING) {
									cellValue = row.getCell(j).getStringCellValue();
									if (cellValue == null || "".equals(cellValue)) {
										continue;
									}
									// 对日期格式进行校验
									String format = isDateFormat(cellValue.toString());
									if (StringHelper.isAllEmpty(format)) {
										throw new BusinessException("第" + (i + 1) + "行【" + excelColumn.title()
												+ "】格式不正确，支持格式为：yyyy-MM-dd、yyyy/MM/dd、yyyy.MM.dd");
									}
									SimpleDateFormat dateFormat = new SimpleDateFormat(format);
									cellValue = dateFormat.parse(cellValue.toString());
								}

								if (cellValue != null) {
									if (obj == null) {
										obj = clazz.newInstance();
									}
									//不允许为空
								} else if (!(excelColumn.allowBlank())) {
									throw new NullPointerException();
								} else {
									continue;
								}
							} catch (NullPointerException e) {
								if (!excelColumn.allowBlank()) {
									throw new BusinessException("第" + (i + 1) + "行【" + excelColumn.title() + "】列不允许为空");
								}
								continue;
							} catch (ParseException e) {
								e.printStackTrace();
								throw new BusinessException("第" + (i + 1) + "行【" + excelColumn.title() + "】格式不正确");
							}
						}
						// 判断字段是否是基本类型或包装类
						else if (TYPE.containsKey(fieldType.getSimpleName())) {
							String className = fieldType.getSimpleName();
							try {
								// 字段为整形
								if (TYPE.get(className) == Integer.class) {
									row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
									String value = row.getCell(j).getStringCellValue();
									if ("".equals(value)) {
										if (excelColumn.allowBlank()) {
											cellValue = null;
										} else {
											throw new BusinessException("第" + (i + 1) + "行【" + excelColumn.title()
													+ "】列不允许为空");
										}
									} else {
										cellValue = value != null ? Integer.valueOf(value) : null;
									}
									if (obj == null) {
										obj = clazz.newInstance();
									}
								}
								// 字段类型为BigDecimal
								else if (TYPE.get(className) == BigDecimal.class) {
									row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
									String tmp = row.getCell(j).getStringCellValue();
									if (tmp.equals("")) {
										if (excelColumn.allowBlank()) {
											cellValue = null;
										} else {
											throw new BusinessException("第" + (i + 1) + "行【" + excelColumn.title()
													+ "】列不允许为空");
										}
									} else {
										cellValue = new BigDecimal(tmp).setScale(excelColumn.scale(), RoundingMode.HALF_UP);
									}
									if (obj == null) {
										obj = clazz.newInstance();
									}
								}
								// 字段类型为float或Float
								else if (TYPE.get(className) == Float.class) {
									Double value = row.getCell(j).getNumericCellValue();
									int scale = excelColumn.scale();
									BigDecimal decimal = new BigDecimal(value);
									cellValue = value != null
											? decimal.setScale(scale, RoundingMode.HALF_UP).floatValue() : null;
									if (obj == null) {
										obj = clazz.newInstance();
									}
								}
								// 字段类型为long或Long
								else if (TYPE.get(className) == Long.class) {
									Double value = row.getCell(j).getNumericCellValue();
									cellValue = value != null ? value.longValue() : null;
									if (obj == null) {
										obj = clazz.newInstance();
									}
								}
								// 字段为double或Double
								else if (TYPE.get(className) == Double.class) {
									Double value = row.getCell(j).getNumericCellValue();
									int scale = excelColumn.scale();
									BigDecimal decimal = new BigDecimal(value);
									cellValue = decimal.setScale(scale, RoundingMode.HALF_UP).doubleValue();
									if (obj == null) {
										obj = clazz.newInstance();
									}
								}
								// 字段为布尔(boolean)
								else if (TYPE.get(className) == Boolean.class) {
									row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
									String tmp = row.getCell(j).getStringCellValue().trim();
									if (tmp.equals("是")) {
										cellValue = true;
									} else if (tmp.equals("否")) {
										cellValue = false;
									} else {
										throw new BusinessException("第" + (i + 1) + "行【" + excelColumn.title()
												+ "】列数值格式不正确");
									}
									if (obj == null) {
										obj = clazz.newInstance();
									}
								}
							} catch (NullPointerException e) {
								if (!excelColumn.allowBlank()) {
									throw new BusinessException("第" + (i + 1) + "行【" + excelColumn.title() + "】列不允许为空");
								}
								continue;
							} catch (NumberFormatException e) {
								throw new BusinessException("第" + (i + 1) + "行【" + excelColumn.title() + "】列数值格式不正确");
							}
						}
						// 字段属于布尔类型(Boolean)
						else if (fieldType == Boolean.class) {
							try {
								row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
								// cellValue =
								// row.getCell(j).getStringCellValue();
								String tmp = row.getCell(j).getStringCellValue().trim();
								if (tmp.equals("是")) {
									cellValue = true;
								} else if (tmp.equals("否")) {
									cellValue = false;
								} else {
									throw new BusinessException("第" + (i + 1) + "行【" + excelColumn.title()
											+ "】列数值格式不正确");
								}
								if (obj == null) {
									obj = clazz.newInstance();
								}
							} catch (NullPointerException e) {
								continue;
							}
						}
						// 字段为枚举类型
						else if (fieldType.isEnum()) {
							try {
								String value = row.getCell(j).getStringCellValue();
								if ("".equals(value)) {// 如果该字段为空字符串,则判断是否允许为空
									if (excelColumn.allowBlank()) {
										continue;
									} else {
										throw new BusinessException("第" + (i + 1) + "行【" + excelColumn.title() + "】 "
												+ value + " 不允许为空");
									}
								}
								Method method = ((Class<?>) mapping.get(j)[1]).getDeclaredMethod("getMatchByName", String.class);
								cellValue = method.invoke(obj, value);
								if (cellValue != null) {
									if (obj == null) {
										obj = clazz.newInstance();
									}
								} else {
									throw new BusinessException("第" + (i + 1) + "行【" + excelColumn.title() + "】 "
											+ value + " 不存在");
									// continue;
								}
							} catch (NullPointerException e) {
								// 空值,继续读下一个字段
								continue;
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
								throw new BusinessException("未找到枚举的getMatchByName方法!");
							} catch (InvocationTargetException e) {
								e.printStackTrace();
								throw new BusinessException(e.getMessage());
							}
						}
						// 非java支持的基本类型、Date、String则忽略该字段
						else {
							continue;
						}
						if (cellValue != null) {
							// 为对象赋值
							Field f = (Field) mapping.get(j)[0];
							f.setAccessible(true);
							f.set(obj, cellValue);
						}
					}
					if (obj != null) {
						result.add(obj);
					}
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
				throw new BusinessException("创建对象失败");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new BusinessException("创建对象失败");
			} catch (IllegalStateException e) {
				e.printStackTrace();
				throw new BusinessException("第" + (i + 1) + "行 【"
						+ ((ExcelColumn) mapping.get(currentColumn)[2]).title() + "】列数据格式不正确");
			}
		}
		// 返回当前sheet解析后的结果
		return result;
	}

	/**
	 * 导出excel
	 *
	 * @param data
	 * @param clazz
	 * @param excelTitle
	 * @return
	 * @throws Exception
	 */
	public static <D> Workbook export(Collection<D> data, Class<D> clazz, String excelTitle) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();

		//第一行
		Row firstRow = sheet.createRow(0);
		Cell firstcell = firstRow.createCell(0);
		firstcell.setCellValue(excelTitle);
		//标题文字居中
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		firstcell.setCellStyle(cellStyle);

		// 获取所有字段
		Field[] declaredFields = clazz.getDeclaredFields();
		// Map<Field, ExcelColumn> columnMapping = new HashMap<Field,
		// ExcelColumn>();
		List<ExcelColumn> excelColumns = new ArrayList<ExcelColumn>();
		List<String> fieldNames = new ArrayList<String>();
		List<Field> fields = new ArrayList<Field>();
		List<String> fieldType = new ArrayList<String>();
		// 获取添加注解的字段
		for (Field field : declaredFields) {
			ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
			if (annotation != null) {
				excelColumns.add(annotation);
				fieldNames.add(field.getName());
				fields.add(field);
				fieldType.add(field.getType().getSimpleName());
				// 将filed与注解进行映射
				// columnMapping.put(field, annotation);
			}
		}

		//合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, fields.size()));

		// 创建第二行为标题行
		Row title = sheet.createRow(1);
		// 为标题行赋值
		for (int i = 0; i < fields.size(); i++) {
			title.createCell(i).setCellValue(excelColumns.get(i).title());
		}

		int rowCount = 2;
		for (Object object : data) {
			/*
			 * if (rowCount == 65534) { sheet = workbook.createSheet(); rowCount
			 * = 1; }
			 */
			Row dataRow = sheet.createRow(rowCount);
			for (int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				field.setAccessible(true);
				try {
					// 获取字段值
					Object fieldValue = field.get(object);

					Cell cell = dataRow.createCell(i);
					if (fieldValue == null) {
						continue;
					}

					String fieldtype = fieldType.get(i);
					if (TYPE.get(fieldtype) == String.class) {
						//是否是日期类型
						ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
						boolean datetype = excelColumn.dateType();
						//字段值
						String fieldValueStr = fieldValue.toString();
						if (datetype) {
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							HSSFCellStyle dateCellStyle = workbook.createCellStyle();
							String dateFormat = excelColumn.dateFormat();
							short df = workbook.createDataFormat().getFormat(dateFormat);
							dateCellStyle.setDataFormat(df);
							cell.setCellStyle(dateCellStyle);

							Date fieldDateValue = DateHelper.formatStringToDate(fieldValueStr, dateFormat);
							cell.setCellValue(fieldDateValue);
						} else {
							cell.setCellValue(fieldValueStr);
						}
					} else if (TYPE.get(fieldtype) == Integer.class || TYPE.get(fieldtype) == Float.class
							|| TYPE.get(fieldtype) == Double.class || TYPE.get(fieldtype) == Long.class) {
						cell.setCellValue(Double.valueOf(fieldValue.toString()));
					} else if (TYPE.get(fieldtype) == BigDecimal.class) {
						cell.setCellValue(fieldValue.toString());
					} else if (TYPE.get(fieldtype) == Boolean.class) {
						cell.setCellValue(Boolean.valueOf(fieldValue.toString()));
					} else if (TYPE.get(fieldtype) == Date.class) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						HSSFCellStyle dateCellStyle = workbook.createCellStyle();
						short df = workbook.createDataFormat().getFormat(field.getAnnotation(ExcelColumn.class).dateFormat());
						dateCellStyle.setDataFormat(df);
						cell.setCellStyle(dateCellStyle);
						cell.setCellValue((Date) fieldValue);
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			rowCount++;
		}
		return workbook;
	}
}

