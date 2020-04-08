package com.juanjo.proyecto.dao;

import java.util.List;
import java.util.Map;

import com.juanjo.proyecto.model.CanvasjsChartData;

public class CanvasjsChartDaoImpl implements CanvasjsChartDao {

	@Override
	public List<List<Map<Object, Object>>> getCanvasjsChartData() {
		return CanvasjsChartData.getCanvasjsDataList();
	}

}