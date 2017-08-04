package com.bycc.enumItem;

/**
 * 民族枚举
 *
 */
public enum Nation {
	HAN("01", "汉"), MENGGU("02", "蒙古"), HUI("03", "回"), ZANG("04", "藏"), WEIWUER("05", "维吾尔"), MIAO("06", "苗"),
	YI("07", "彝族"), ZHUANG("08", "壮族"), BUYI("09", "布依族"), CHAOXIAN("10", "朝鲜族"), MAN("11", "满族"), DONG("12", "侗族"), YAO("13", "瑶族")
	, BAI("14", "白族"), TUJIA("15", "土家族"), HANI("16", "哈尼族"), HASAKE("17", "哈萨克族"), DAI("18", "傣族"), LI("19", "黎族"), SULI("20", "傈僳族")
	, WA("21", "佤族")
	, YU("22", "畬族")
	, GAOSHAN("23", "高山族")
	, LAHU("24", "拉祜族")
	, SHUI("25", "水族")
	, DONGXIANG("26", "东乡族")
	, NAXI("27", "纳西族")
	, JINGPO("28", "景颇族")
	, KEERKEZI("29", "柯尔克孜族")
	, TU("30", "土族")
	, DAWOER("31", "达斡尔族")
	, MULAO("32", "仫佬族")
	, QIANG("33", "羌族")
	, BULANG("34", "布朗族")
	, SALA("35", "撒拉族")
	, MAONAN("36", "毛南族")
	, GELAO("37", "仡佬族")
	, XIBO("38", "锡伯族")
	, ACHANG("39", "阿昌族")
	, PUMI("40", "普米族")
	, TAJIKE("41", "塔吉克族")
	, NU("42", "怒族")
	, WUZIBIEKE("43", "乌兹别克族")
	, ELUOSI("44", "俄罗斯族")
	, EWENKE("45", "鄂温克族")
	, DEANG("46", "德昂族")
	, BAOAN("47", "保安族")
	, YUGU("48", "裕固族")
	, JING("49", "京族")
	, TATAER("50", "塔塔尔族")
	, DULONG("51", "独龙族")
	, ELUNCHUN("52", "鄂伦春族")
	, HEZHE("53", "赫哲族")
	, MENBA("54", "门巴族")
	, LUOBA("55", "珞巴族")
	, JINUO("56", "基诺族")
	, OTHER("57", "其他")
	, FOREIGN("58", "外国血统");

	// 民族代码
	private String code;

	private String value;

	private Nation(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public static Nation getByCode(String code) {
		for(Nation nation: values()){
			if (nation.getCode().equals(code)) {
				return nation;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
