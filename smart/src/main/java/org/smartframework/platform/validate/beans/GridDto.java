package org.smartframework.platform.validate.beans;

public class GridDto {

    //TODO 待优化，此处引入了一个脏变量--行号，用于校验后定位到行
    private Integer _row_id;

    public Integer get_row_id() {
        return _row_id;
    }

    public void set_row_id(Integer _row_id) {
        this._row_id = _row_id;
    }
}
