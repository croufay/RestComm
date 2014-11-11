package org.mobicents.servlet.restcomm.http.statistics;
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


import org.apache.commons.configuration.Configuration;
import org.mobicents.servlet.restcomm.annotations.concurrency.ThreadSafe;
import org.mobicents.servlet.restcomm.http.converter.AbstractConverter;
import org.mobicents.servlet.restcomm.statistics.StatisticsTopTenCallersList;
import org.mobicents.servlet.restcomm.statistics.StatisticsTopTenCallers;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * @author Charles.Roufay@telestax.com(Charles Roufay)
 */
@ThreadSafe
public final class StatisticsTopTenSMSListConverter extends AbstractConverter {
    public StatisticsTopTenSMSListConverter(final Configuration configuration) {
        super(configuration);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean canConvert(final Class klass) {
        return StatisticsTopTenCallersList.class.equals(klass);
    }

    @Override
    public void marshal(final Object object, final HierarchicalStreamWriter writer, final MarshallingContext context) {
        final StatisticsTopTenCallersList list = (StatisticsTopTenCallersList) object;
        writer.startNode("Statistics");
        for (final StatisticsTopTenCallers statistics : list.getStatistics()) {
            context.convertAnother(statistics);
        }
        writer.endNode();
    }
}
