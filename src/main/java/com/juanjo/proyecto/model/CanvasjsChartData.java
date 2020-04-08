package com.juanjo.proyecto.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CanvasjsChartData {

	static Map<Object, Object> map = null;
	static List<List<Map<Object, Object>>> list = new ArrayList<List<Map<Object, Object>>>();
	static List<Map<Object, Object>> dataPoints1 = new ArrayList<Map<Object, Object>>();
	static List<Map<Object, Object>> dataPoints2 = new ArrayList<Map<Object, Object>>();
	static List<Map<Object, Object>> dataPoints3 = new ArrayList<Map<Object, Object>>();
	static List<Map<Object, Object>> dataPoints4 = new ArrayList<Map<Object, Object>>();

	static {
		map = new HashMap<Object, Object>();
		map.put("x", 1199125800000L);
		map.put("y", 91.9);
		dataPoints1.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1230748200000L);
		map.put("y", 85.1);
		dataPoints1.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1262284200000L);
		map.put("y", 82.8);
		dataPoints1.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1293820200000L);
		map.put("y", 74.6);
		dataPoints1.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1325356200000L);
		map.put("y", 69);
		dataPoints1.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1356978600000L);
		map.put("y", 60.8);
		dataPoints1.add(map);

		map = new HashMap<Object, Object>();
		map.put("x", 1199125800000L);
		map.put("y", 13.5);
		dataPoints2.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1230748200000L);
		map.put("y", 12.3);
		dataPoints2.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1262284200000L);
		map.put("y", 11.8);
		dataPoints2.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1293820200000L);
		map.put("y", 10.3);
		dataPoints2.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1325356200000L);
		map.put("y", 9.4);
		dataPoints2.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1356978600000L);
		map.put("y", 8.4);
		dataPoints2.add(map);

		map = new HashMap<Object, Object>();
		map.put("x", 1199125800000L);
		map.put("y", 7.1);
		dataPoints3.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1230748200000L);
		map.put("y", 6.7);
		dataPoints3.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1262284200000L);
		map.put("y", 6.7);
		dataPoints3.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1293820200000L);
		map.put("y", 6.5);
		dataPoints3.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1325356200000L);
		map.put("y", 5.8);
		dataPoints3.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1356978600000L);
		map.put("y", 4.9);
		dataPoints3.add(map);

		map = new HashMap<Object, Object>();
		map.put("x", 1199125800000L);
		map.put("y", 28.2);
		dataPoints4.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1230748200000L);
		map.put("y", 23.7);
		dataPoints4.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1262284200000L);
		map.put("y", 21.7);
		dataPoints4.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1293820200000L);
		map.put("y", 19.7);
		dataPoints4.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1325356200000L);
		map.put("y", 18.3);
		dataPoints4.add(map);
		map = new HashMap<Object, Object>();
		map.put("x", 1356978600000L);
		map.put("y", 17.5);
		dataPoints4.add(map);

		list.add(dataPoints1);
		list.add(dataPoints2);
		list.add(dataPoints3);
		list.add(dataPoints4);
	}

	public static List<List<Map<Object, Object>>> getCanvasjsDataList() {
		return list;
	}
}