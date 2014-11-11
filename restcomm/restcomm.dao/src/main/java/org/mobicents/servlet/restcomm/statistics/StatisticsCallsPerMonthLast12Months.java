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
 * Represent a calls per month last 12 months Statistics
 *
 * @author charles.roufay@telestax.com(Charles Roufay)
 */
@Immutable
public final class StatisticsCallsPerMonthLast12Months {
   private  String month;
   private  String month_name;
   private  String year;
   private  String number_of_calls_per_month;




public StatisticsCallsPerMonthLast12Months (String month, String month_name, String year, String number_of_calls_per_month ){

  this.month = month;
  this.month_name = month_name;
  this.year = year;
  this.number_of_calls_per_month = number_of_calls_per_month;
       }



public String getMonth(){
return this.month;
   }

 public String getMonthName(){
return this.month_name;
   }

public String getYear(){
return this.year;
   }

public String getNumberOfCallsPerMonth(){
return this.number_of_calls_per_month;
   }

}
