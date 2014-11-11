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
package org.mobicents.servlet.restcomm.dao;

import java.util.List;

import org.mobicents.servlet.restcomm.statistics.StatisticsCallsPerDayLast30Days;
import org.mobicents.servlet.restcomm.statistics.StatisticsCallsPerMonthLast12Months;
import org.mobicents.servlet.restcomm.statistics.StatisticsSMSPerDayLast30Days;
import org.mobicents.servlet.restcomm.statistics.StatisticsSMSPerMonthLast12Months;
import org.mobicents.servlet.restcomm.statistics.StatisticsTopTenCallers;
import org.mobicents.servlet.restcomm.statistics.StatisticsTopTenSMS;
import org.mobicents.servlet.restcomm.statistics.StatisticsTotalCallsLast24Hours;
import org.mobicents.servlet.restcomm.statistics.StatisticsTotalSMSLast24Hours;


/**
 * @author charles.roufay@telestax.com (Charles Roufay)
*/
public interface StatisticsDao {



List<StatisticsTopTenCallers> getTopTenCallers();
StatisticsTotalCallsLast24Hours getTotalCallsLast24Hours ();

List<StatisticsTopTenSMS> getTopTenSMS();
StatisticsTotalSMSLast24Hours getTotalSMSLast24Hours ();

List<StatisticsSMSPerMonthLast12Months> getSMSPerMonthLast12Months();
List<StatisticsSMSPerDayLast30Days> getSMSPerDayLast30Days();

List<StatisticsCallsPerMonthLast12Months> getCallsPerMonthLast12Months();
List<StatisticsCallsPerDayLast30Days> getCallsPerDayLast30Days();

}
