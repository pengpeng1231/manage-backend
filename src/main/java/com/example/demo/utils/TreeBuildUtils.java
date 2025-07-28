package com.example.demo.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class TreeBuildUtils {
    public static <T> List<T> build(
            List<T> list,
            Function<T, Serializable> getId,
            Function<T, Serializable> getParentId,
            Function<T, List<T>> getChildren,
            BiConsumer<T, List<T>> setChildren) {

        Map<Serializable, T> map = new HashMap<>();
        List<T> roots = new ArrayList<>();

        // 第一遍遍历：存入map并找出根节点
        for (T item : list) {
            Serializable id = getId.apply(item);
            Serializable parentId = getParentId.apply(item);

            map.put(id, item);

            if (parentId == null || "0".equals(parentId.toString())) {
                roots.add(item);
            }
        }

        // 第二遍遍历：建立父子关系
        for (T item : list) {
            Serializable parentId = getParentId.apply(item);
            if (parentId != null && !"0".equals(parentId.toString())) {
                T parent = map.get(parentId);

                if (parent != null) {
                    List<T> children = getChildren.apply(parent);

                    if (children == null) {
                        children = new ArrayList<>();
                        setChildren.accept(parent, children);
                    }

                    children.add(item);
                }
            }
        }

        return roots;
    }
}