package controllers.rest.factories;

import java.lang.reflect.Type;
import java.util.Date;

import flexjson.ObjectBinder;
import flexjson.ObjectFactory;

public class DateFactory implements ObjectFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see flexjson.ObjectFactory#instantiate(flexjson.ObjectBinder,
	 * java.lang.Object, java.lang.reflect.Type, java.lang.Class)
	 */
	@Override
	public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
		if (value instanceof Number) {
			return new Date(((Number) value).longValue());
		} else {
			throw context.cannotConvertValueToTargetType(value, Long.class);
		}
	}

}