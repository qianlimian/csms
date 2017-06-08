package org.smartframework.common.kendo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.smartframework.utils.helper.DateHelper;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Filter {

    private String field;

    private String operator;

    private Object value;

    private Boolean ignoreCase;

    private String type;

    public String getSymbolField() {
        return this.field.replace(".","");
    }

    public String getSymbolOperator () {
        Map<String, String> map = new HashMap<String, String>() {
            {
                put("eq", "=");
                put("neq", "<>");
                put("startswith", "like");
                put("contains", "like");
                put("doesnotcontain", "not like");
                put("endswith", "like");
                put("gte", ">=");
                put("gt", ">");
                put("lte", "<=");
                put("lt", "<");
            }
        };
        return map.get(this.operator);
    }

    public Object getSymbolValue() {
        switch (this.operator) {
            case "contains":
            case "doesnotcontain":
                return "%" + this.value + "%";
            case "startwith":
                return this.value + "%";
            case "endswith":
                return "%" + this.value;
            default:
                Object result = this.value;
                if (this.type.equals("date")) {
                    try {
                        result = DateHelper.formatUTCToDate(this.value.toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (this.type.startsWith("enum")) {
                    try {
                        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
                        Matcher matcher = pattern.matcher(this.type);
                        if (matcher.find()) {
                            String className = matcher.group().substring(1, matcher.group().length() - 1);
                            Class clazz = Class.forName(className);
                            if (clazz.isEnum()) {
                                for (Object o : clazz.getEnumConstants()) {
                                    Enum e = (Enum) o;
                                    if (e.name().equals(this.value)) {
                                        result = e;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return result;
        }

    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Boolean getIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(Boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
