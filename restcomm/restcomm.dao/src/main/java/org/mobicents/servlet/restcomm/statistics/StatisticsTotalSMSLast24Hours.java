package org.mobicents.servlet.restcomm.statistics;

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

import org.apache.http.annotation.Immutable;


/**
 * Represent a TopTenCaller  Statistics
 *
 * @author charles.roufay@telestax.com(Charles Roufay)
 */
@Immutable
public class StatisticsTotalSMSLast24Hours {

private final String totalSMSLast24Hours;

public StatisticsTotalSMSLast24Hours(String totalSMSLast24Hours){
this.totalSMSLast24Hours = totalSMSLast24Hours;
}

public final String getTotalSMSLast24Hours(){
return this.totalSMSLast24Hours;
}
}