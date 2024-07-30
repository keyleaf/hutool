package cn.hutool.poi.excel.cell.setters;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.cell.CellSetter;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @author keyleaf
 **/
public class IterableCellSetter implements CellSetter {

	private final Iterable<?> value;

	public IterableCellSetter(Iterable<?> value) {
		this.value = value;
	}

	@Override
	public void setValue(Cell cell) {
		cell.setCellValue(CollUtil.join(value, "\r\n"));
	}
}
