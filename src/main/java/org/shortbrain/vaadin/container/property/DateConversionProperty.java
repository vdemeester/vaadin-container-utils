package org.shortbrain.vaadin.container.property;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property;

/**
 * {@link Property} that wrap another convert the content of another property of
 * type String to a {@link Date}.
 * 
 * @author Vincent Demeester <vdemeester@sbr.io>
 * 
 */
public class DateConversionProperty implements Property {

	private static final long serialVersionUID = -6473750281491800654L;
	private static final Logger log = LoggerFactory
			.getLogger(DateConversionProperty.class);

	private final Property property;
	private final DateFormat dateFormat;

	public DateConversionProperty(Property property, String pattern) {
		this.property = property;
		this.dateFormat = new SimpleDateFormat(pattern);
	}

	@Override
	public Object getValue() {
		Date date = null;
		try {
			date = dateFormat.parse((String) property.getValue());
		} catch (ParseException e) {
			log.info("Error while parsing the date property", e);
		} catch (NullPointerException e) {
			log.info("Error while parsing the date property", e);
		}
		return date;
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException,
			ConversionException {
		String dateStr = dateFormat.format((Date) newValue);
		this.property.setValue(dateStr);
	}

	@Override
	public Class<?> getType() {
		return Date.class;
	}

	@Override
	public boolean isReadOnly() {
		return property.isReadOnly();
	}

	@Override
	public void setReadOnly(boolean newStatus) {
		property.setReadOnly(newStatus);
	}

}