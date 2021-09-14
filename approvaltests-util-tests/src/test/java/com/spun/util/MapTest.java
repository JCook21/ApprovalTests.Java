package com.spun.util;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class MapTest
{
  @Test
  public void test()
  {
    Map<String, Object> p = params("name", "Llewellyn").and("age", 40).and("date", DateUtils.parse("2001/01/01"));
    Approvals.verify(p);
  }
  @Test
  public void testWithSortedMap()
  {
    SortedMap<Integer, String> p = new TreeMap<>(Collections.reverseOrder());
    p.put(1, "one");
    p.put(2, "two");
    p.put(3, "three");
    p.put(4, "four");
    Approvals.verify(p);
  }
  public static MapBuilder<String, Object> params(String key, Object value)
  {
    return new MapBuilder<>(key, value);
  }
}
