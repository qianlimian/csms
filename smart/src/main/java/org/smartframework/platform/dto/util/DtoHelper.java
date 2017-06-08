package org.smartframework.platform.dto.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang.StringUtils;
import org.smartframework.platform.dictionary.bean.entry.EnumEntry;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.platform.dto.annotation.DtoProperty;
import org.smartframework.platform.dto.exception.DtoException;
import org.smartframework.utils.FrameUtil;
import org.smartframework.utils.helper.DateHelper;
import org.smartframework.utils.helper.FormatException;
import org.smartframework.utils.helper.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DtoHelper {
	private static final Logger LOG = LoggerFactory.getLogger(DtoHelper.class);
	private static final Map<Field, DtoProperty> DTO_PROPERTY_MAP = new ConcurrentHashMap<Field, DtoProperty>();
	private static final Map<Class<?>, DtoClass> DTO_CLASS_MAP = new ConcurrentHashMap<Class<?>, DtoClass>();
  
	public static <T> T build(Class<T> dtoBeanClass, Object... entities) throws DtoException {
		if (entities == null) {
			return null;
		}
		if (!dtoBeanClass.isAnnotationPresent(DtoClass.class)) {
			throw new DtoException(DtoException.ExceptionType.Annotation);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		for (Object o : entities) {
			if (o != null) {
				map.put(o.getClass().getCanonicalName(), o);
			}
		}
		try {
			T dto = dtoBeanClass.newInstance();
			buildDTO(dto, map);
			return dto;
		} catch (InstantiationException e) {
			throw new DtoException(e, dtoBeanClass);
		} catch (IllegalAccessException e) {
			throw new DtoException(e, dtoBeanClass);
		}
	}
  
	private static <T> void buildDTO(T dto, Map<String, Object> map) throws DtoException {
		readClass(dto.getClass());
		DtoClass dtoClass = (DtoClass) DTO_CLASS_MAP.get(dto.getClass());
		if (dtoClass == null) {
			return;
		}
		for (Class<?> dtoEntity : dtoClass.entities()) {
			Object entityEntity = map.get(dtoEntity.getCanonicalName());
			if (entityEntity != null) {
				for (Field dtoField : dto.getClass().getDeclaredFields()) {
					DtoProperty dtoProperty = (DtoProperty) DTO_PROPERTY_MAP.get(dtoField);
					if (dtoProperty != null) {
						if (dtoProperty.entityClass().equals(dtoEntity)) {
							String property = dtoProperty.entityProperty();
							if ((property == null) || (property.length() == 0)) {
								property = dtoField.getName();
							}
							try {
								Object value = PropertyHelper.getProperty(entityEntity, property);
								if (value != null) {
									if ((!dtoField.getType().equals(List.class)) && (!dtoField.getType().equals(Set.class))) {
										if (isEnumEntryClass(entityEntity, property).booleanValue()) {
											EnumEntry e = (EnumEntry) getEnumEntryObject(entityEntity, property);
											if (e != null) {
												if (property.endsWith(".key")) {
													PropertyHelper.setProperty(dto, dtoField.getName(), value);
												} else {
													PropertyHelper.setProperty(dto, dtoField.getName(), value);
												}
											}
										} else if (isSame(dtoField, entityEntity, property)) {
											PropertyHelper.setProperty(dto, dtoField.getName(), value);
										} else if ((dtoField.getType().equals(String.class))
												&& (PropertyHelper.getPropertyClass(entityEntity, property).equals(Date.class))) {
											String propValue = DateHelper.formatDateToString((Date) value, "yyyy-MM-dd HH:mm:ss");
											PropertyHelper.setProperty(dto, dtoField.getName(), propValue);
										} else if ((dtoField.getType().equals(Date.class))
												&& (PropertyHelper.getPropertyClass(entityEntity, property).equals(String.class))) {
											Date propValue = null;
											try {
												propValue = DateHelper.formatStringToDate((String) value, "yyyy-MM-dd HH:mm:ss");
											} catch (ParseException e) {
												propValue = null;
											}
											PropertyHelper.setProperty(dto, dtoField.getName(), propValue);
										} else if ((dtoField.getType().equals(String.class))
												&& (PropertyHelper.getPropertyClass(entityEntity, property).equals(Integer.class))) {
											String propValue = value == null ? null: String.valueOf(value);
											PropertyHelper.setProperty(dto, dtoField.getName(), propValue);
										} else if ((dtoField.getType().equals(Integer.class))
												&& (PropertyHelper.getPropertyClass(entityEntity, property).equals(String.class))) {
											String svalue = (String) value;
											Integer propValue = StringHelper.isAnyEmpty(new String[] { svalue.trim() }) ? null : Integer.valueOf(svalue);
											PropertyHelper.setProperty(dto, dtoField.getName(), propValue);
										} else if ((dtoField.getType().equals(String.class))
												&& (PropertyHelper.getPropertyClass(entityEntity, property).equals(BigDecimal.class))) {
											String propValue = value == null ? null : ((BigDecimal) value).toString();
											PropertyHelper.setProperty(dto, dtoField.getName(), propValue);
										} else if ((dtoField.getType().equals(BigDecimal.class))
												&& (PropertyHelper.getPropertyClass(entityEntity, property).equals(String.class))) {
											String svalue = (String) value;
											BigDecimal propValue = StringHelper.isAnyEmpty(new String[] { svalue.trim() }) ? null : new BigDecimal(svalue);
											PropertyHelper.setProperty(dto, dtoField.getName(), propValue);
										}
									}
								}
							} catch (Exception e) {
								throw new DtoException(e, dto.getClass());
							}
						}
					}
				}
			}
		}
	}

	private static void readClass(Class<?> dtoClass) {
		if (!DTO_CLASS_MAP.containsKey(dtoClass)) {
			DtoClass dc = (DtoClass) dtoClass.getAnnotation(DtoClass.class);
			if (dc != null) {
				DTO_CLASS_MAP.put(dtoClass, dc);
			}
		}
		for (Field f : dtoClass.getDeclaredFields()) {
			if ((f.isAnnotationPresent(DtoProperty.class)) && (!DTO_PROPERTY_MAP.containsKey(f))) {
				DTO_PROPERTY_MAP.put(f, (DtoProperty) f.getAnnotation(DtoProperty.class));
			}
		}
	}
  
	private static Boolean isEnumEntryClass(Object entity, String property) {
		String[] names = property.split("\\.");
		Class<?> clazz = entity.getClass();
		for (int i = 0; i < names.length; i++) {
			clazz = PropertyHelper.getClassSimplePropertyClass(clazz, names[i]);
			if (clazz.isEnum()) {
				return Boolean.valueOf(FrameUtil.isClassImplementInterface(clazz, EnumEntry.class));
			}
		}
		return Boolean.valueOf(false);
	}
  
	private static Object getEnumEntryObject(Object entity, String property) throws Exception {
		String[] names = property.split("\\.");
		Class<?> clazz = entity.getClass();
		StringBuffer pathToEnumEntry = new StringBuffer();
		for (int i = 0; i < names.length; i++) {
			clazz = PropertyHelper.getClassSimplePropertyClass(clazz, names[i]);
			pathToEnumEntry.append(names[i] + ".");
			if ((clazz.isEnum()) && (FrameUtil.isClassImplementInterface(clazz, EnumEntry.class))) {
				String path = pathToEnumEntry.toString();
				return PropertyHelper.getProperty(entity, path.substring(0, path.length() - 1));
			}
		}
		return null;
	}

	private static boolean isSame(Field field, Object entity, String property) throws SecurityException, NoSuchFieldException {
		return field.getType().equals(PropertyHelper.getPropertyClass(entity, property));
	}
  
	public static <E, D> E dismantle(Class<E> entityClass, D dto) throws DtoException {
		if (!dto.getClass().isAnnotationPresent(DtoClass.class)) {
			throw new DtoException(DtoException.ExceptionType.Annotation);
		}
		try {
			E entity = entityClass.newInstance();
			dismantle(entity, dto);
			return entity;
		} catch (InstantiationException e) {
			throw new DtoException(e, dto.getClass());
		} catch (IllegalAccessException e) {
			throw new DtoException(e, dto.getClass());
		}
	}
  
	public static <E, D> void dismantle(E entity, D dto) throws DtoException {
		readClass(dto.getClass());
		for (Field dtoField : dto.getClass().getDeclaredFields()) {
			if (DTO_PROPERTY_MAP.containsKey(dtoField)) {
				DtoProperty dp = (DtoProperty) DTO_PROPERTY_MAP.get(dtoField);
				if (dp.entityClass().equals(entity.getClass())) {
					try {
						if (!dp.readOnly()) {
							Object value = PropertyHelper.getProperty(dto, dtoField.getName());
							if (value != null || dp.nullable()) {
								String property = dp.entityProperty();
								if (property == null || property.length() == 0) {
									property = dtoField.getName();
								}
								if ((!property.equalsIgnoreCase("id")) && ((property.length() <= 3) 
										|| (!StringUtils.right(property, 3).equalsIgnoreCase(".id")))) {
									if (value instanceof String && dp.trim()) {
										value = ((String) value).trim();
									}
									if ((!dtoField.getType().equals(List.class)) && (!dtoField.getType().equals(Set.class))) {
										if (isSame(dtoField, entity, property)) {
											PropertyHelper.setProperty(entity, property, value);
										} else if ((dtoField.getType().equals(String.class))
												&& (PropertyHelper.getPropertyClass(entity, property).equals(Date.class))) {
											Date propValue = null;
											try {
												propValue = DateHelper.formatStringToDate((String) value, "yyyy-MM-dd HH:mm:ss");
											} catch (ParseException e) {
												propValue = null;
											}
											PropertyHelper.setProperty(entity, property, propValue);
										} else if ((dtoField.getType().equals(Date.class))
												&& (PropertyHelper.getPropertyClass(entity, property).equals(String.class))) {
											String propValue = DateHelper.formatDateToString((Date) value, "yyyy-MM-dd HH:mm:ss");
											PropertyHelper.setProperty(entity, property, propValue);
										} else if ((dtoField.getType().equals(String.class))
												&& (PropertyHelper.getPropertyClass(entity, property).equals(Integer.class))) {
											String svalue = (String) value;
											Integer propValue = StringHelper.isAnyEmpty(new String[] { svalue.trim() }) ? null : Integer.valueOf(svalue);
											PropertyHelper.setProperty(entity, property, propValue);
										} else if ((dtoField.getType().equals(Integer.class))
												&& (PropertyHelper.getPropertyClass(entity, property).equals(String.class))) {
											String propValue = value == null ? null : String.valueOf(value);
											PropertyHelper.setProperty(entity, property, propValue);
										} else if ((dtoField.getType().equals(String.class))
												&& (PropertyHelper.getPropertyClass(entity, property).equals(BigDecimal.class))) {
											String svalue = (String) value;
											BigDecimal propValue = StringHelper.isAnyEmpty(new String[] { svalue.trim() }) ? null : new BigDecimal(svalue);
											PropertyHelper.setProperty(entity, property, propValue);
										} else if ((dtoField.getType().equals(BigDecimal.class))
												&& (PropertyHelper.getPropertyClass(entity, property).equals(String.class))) {
											String propValue = value == null ? null : ((BigDecimal) value).toString();
											PropertyHelper.setProperty(entity, property, propValue);
										}
									}
								}
							}
						}
					} catch (IllegalAccessException e) {
						throw new DtoException(e, dto.getClass());
					} catch (InvocationTargetException e) {
						throw new DtoException(e, dto.getClass());
					} catch (NoSuchMethodException e) {
						throw new DtoException(e, dto.getClass());
					} catch (SecurityException e) {
						throw new DtoException(e, dto.getClass());
					} catch (NoSuchFieldException e) {
						throw new DtoException(e, dto.getClass());
					} catch (IllegalArgumentException e) {
						throw new DtoException(e, dto.getClass());
					} catch (FormatException e) {
						throw new DtoException(e, dto.getClass());
					}
				}
			}
		}
	}
  
	public static boolean checkDto(Class<?> dtoClass) {
		readClass(dtoClass);
		for (Field dtoField : dtoClass.getDeclaredFields()) {
			if (DTO_PROPERTY_MAP.containsKey(dtoField)) {
				DtoProperty dp = (DtoProperty) DTO_PROPERTY_MAP.get(dtoField);
				String property = dp.entityProperty();
				if ((property == null) || (property.length() == 0)) {
					property = dtoField.getName();
				}
				try {
					Class<?> dtoType = dtoField.getType();
					Class<?> entityType = PropertyHelper.getClassPropertyClass(dp.entityClass(), property);
					if (!dtoType.equals(entityType)) {
						if ((dtoType.equals(Date.class)) && (entityType.equals(String.class))) {
							return true;
						}
						if ((dtoType.equals(String.class)) && (entityType.equals(Date.class))) {
							return true;
						}
						LOG.warn("DTO:" + dtoClass.getCanonicalName() + " 属性:" + dtoField.getName() + "类型错误.");
						return false;
					}
				} catch (Exception e) {
					LOG.warn("DTO:" + dtoClass.getCanonicalName() + " 属性:" + dtoField.getName() + " 异常:" + e.getMessage());
					return false;
				}
			}
		}
		return true;
	}
}

