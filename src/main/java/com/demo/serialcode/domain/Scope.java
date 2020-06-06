package com.demo.serialcode.domain;

/**
 * 序列号类别
 * 
 * @author hongzhaomin
 * 2018年4月4日 下午1:15:28
 */
public enum Scope {
    
    /**
     * 企业序列号
     */
    CLIENTSERIALCODE(1),
    
    /**
     * 全局序列号
     */
    GLOBALSERIALCODE(2);
	
	/**
	 * 下标值。
	 */
	private Integer index;
		
	/**
	 * 使用下标构造枚举。
	 * 构造函数
	 * @param index 用于构造枚举的下标。
	 */
	private Scope(Integer index) {
		this.index = index;
	}

	/**
	 * 返回当前枚举的下标。
	 * @return 返回本枚举的下标值
	 */	
	public Integer index() {
		return index;
	}

	/**
	 * 根据枚举的下标获取对应的枚举。如果没有找到对应的枚举，则返回null。
	 * @param index 枚举的下标值
	 * @return 根据下标找到的枚举。如果没有匹配的枚举，返回空指针null。
	 */	
	public static Scope indexOf(int index) {
        for (Scope item : Scope.values()) {
            if (item.index == index) {
				return item;
            }
        }

		return null;
	}

	/**
	 * 根据枚举的字面值获取对应的枚举。如果没有找到对应的枚举，则返回null。
	 * @param name 枚举的字面值
	 * @return 根据字面值找到的枚举。如果没有匹配的枚举，返回空指针null。
	 */	
	public static Scope nameOf(String name) {
		for (Scope item : Scope.values()) {
			if (item.toString().equals(name)) {
				return item;
			}
		}

		return null;
	}
	
    /**
     * 输出格式为 name(index)
     */
	@Override
	public String toString() {
	    return super.toString() + "(" + this.index + ")";
	}
}
