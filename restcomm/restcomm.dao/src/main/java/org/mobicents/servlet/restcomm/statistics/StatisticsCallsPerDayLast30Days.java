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
 * Represent calls per days last 30 days Statistics
 *
 * @author charles.roufay@telestax.com(Charles Roufay)
 */
@Immutable
public final class StatisticsCallsPerDayLast30Days {
   private  String day;
   private  String month_name;
   private  String day_name;
   private  String number_of_calls_per_day;




public StatisticsCallsPerDayLast30Days (String day, String month_name, String day_name, String number_of_calls_per_day ){

  this.day = day;
  this.month_name = month_name;
  this.day_name = day_name;
  this.number_of_calls_per_day = number_of_calls_per_day;
       }



public String getDay(){
return this.day;
   }

 public String getMonthName(){
return this.month_name;
   }

public String getDayName(){
return this.day_name;
   }

public String getNumberOfCallsPerDay(){
return this.number_of_calls_per_day;
   }

}
