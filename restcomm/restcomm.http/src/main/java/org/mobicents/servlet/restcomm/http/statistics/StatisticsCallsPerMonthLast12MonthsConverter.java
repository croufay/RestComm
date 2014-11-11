/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2014, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This program is free software: you can redistribute it and/or modify
 * under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */
package org.mobicents.servlet.restcomm.http.statistics;

import java.lang.reflect.Type;

import org.apache.commons.configuration.Configuration;
import org.mobicents.servlet.restcomm.annotations.concurrency.ThreadSafe;
import org.mobicents.servlet.restcomm.statistics.StatisticsCallsPerMonthLast12Months;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


/**
 * @author charles.roufay@telestax.com (Charles Roufay)
 */
@ThreadSafe
public final class StatisticsCallsPerMonthLast12MonthsConverter  implements JsonSerializer<StatisticsCallsPerMonthLast12Months> {

    public StatisticsCallsPerMonthLast12MonthsConverter(final Configuration configuration) {
        super();

    }



    @Override
    public JsonElement serialize(final StatisticsCallsPerMonthLast12Months stats, Type type, final JsonSerializationContext context) {
        final JsonObject object = new JsonObject();
        object.addProperty("month", stats.getMonth());
        object.addProperty("year", stats.getYear());
        object.addProperty("monthname", stats.getMonthName() );
        object.addProperty("numberofcallspermonth", stats.getNumberOfCallsPerMonth() );
        return object;
    }








}

