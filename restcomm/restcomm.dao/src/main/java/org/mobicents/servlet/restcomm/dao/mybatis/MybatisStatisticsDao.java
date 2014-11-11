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
package org.mobicents.servlet.restcomm.dao.mybatis;


import static org.mobicents.servlet.restcomm.dao.DaoUtils.readString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.annotation.NotThreadSafe;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mobicents.servlet.restcomm.dao.StatisticsDao;
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
@NotThreadSafe
public final class MybatisStatisticsDao implements StatisticsDao{
    private static final String namespace = "org.mobicents.servlet.sip.restcomm.dao.StatisticsDao.";
    private final SqlSessionFactory sessions;



    public MybatisStatisticsDao(final SqlSessionFactory sessions) {
        super();
        this.sessions = sessions;
    }



// top ten callers of all time

@Override
public List <StatisticsTopTenCallers> getTopTenCallers (){
final SqlSession session = sessions.openSession();
try {
final List<Map<String, Object>> results = session.selectList(namespace + "Toptencallers");
     final List<StatisticsTopTenCallers>  statistics = new ArrayList<StatisticsTopTenCallers>();
     if (results != null && !results.isEmpty()) {
         for ( final Map<String, Object> result : results ) {
 statistics.add(toTopTenCallers(result));

         }
     }

return statistics;
       } finally {
           session.close();
       }
}


private StatisticsTopTenCallers toTopTenCallers(final Map<String, Object> map) {

    final String c_sender = readString(map.get("sender"));
    final String c_recipient = readString(map.get("recipient"));
    final String c_numberofcalls = readString(map.get("numberofcalls"));

    return new StatisticsTopTenCallers(c_sender, c_recipient, c_numberofcalls);
}


//total number of calls in the last 24 hours

@Override
public StatisticsTotalCallsLast24Hours getTotalCallsLast24Hours (){
final SqlSession session = sessions.openSession();
try {
final String result = session.selectOne(namespace + "Totalcallslast24hours");
     StatisticsTotalCallsLast24Hours  statistics = new StatisticsTotalCallsLast24Hours (result);
     if (result != null) {

 statistics = new StatisticsTotalCallsLast24Hours (result);
   }
return statistics;
       } finally {
           session.close();
       }
}

//Top ten sms senders


@Override
public List <StatisticsTopTenSMS> getTopTenSMS (){
final SqlSession session = sessions.openSession();
try {
final List<Map<String, Object>> results = session.selectList(namespace + "Toptensendersms");
     final List<StatisticsTopTenSMS>  statistics = new ArrayList<StatisticsTopTenSMS>();
     if (results != null && !results.isEmpty()) {
         for ( final Map<String, Object> result : results ) {
 statistics.add(toTopTenSMS(result));
         }
     }

return statistics;
       } finally {
           session.close();
       }
}


private StatisticsTopTenSMS toTopTenSMS(final Map<String, Object> map) {

    final String c_sender = readString(map.get("sender"));
    final String c_recipient = readString(map.get("recipient"));
    final String c_numberofsms = readString(map.get("numberofsms"));

    return new StatisticsTopTenSMS(c_sender, c_recipient, c_numberofsms);
}


// total sms sent in the last 24 hours

@Override
public StatisticsTotalSMSLast24Hours getTotalSMSLast24Hours (){
final SqlSession session = sessions.openSession();
try {
final String result = session.selectOne(namespace + "Totalsmslast24hours");
     StatisticsTotalSMSLast24Hours  statistics = new StatisticsTotalSMSLast24Hours (result);
     if (result != null) {

 statistics = new StatisticsTotalSMSLast24Hours (result);
   }
return statistics;
       } finally {
           session.close();
       }
}

//number of calls per day in last 30 days


@Override
public List <StatisticsCallsPerDayLast30Days> getCallsPerDayLast30Days (){
final SqlSession session = sessions.openSession();
try {
final List<Map<String, Object>> results = session.selectList(namespace + "Numberofcallsperdaylast30days");
     final List<StatisticsCallsPerDayLast30Days>  statistics = new ArrayList<StatisticsCallsPerDayLast30Days>();
     if (results != null && !results.isEmpty()) {
         for ( final Map<String, Object> result : results ) {
 statistics.add(toCallsPerDayLast30Days(result));

         }
     }

return statistics;
       } finally {
           session.close();
       }
}


private StatisticsCallsPerDayLast30Days toCallsPerDayLast30Days (final Map<String, Object> map) {

    final String c_day = readString(map.get("day"));
    final String c_monthname = readString(map.get("month_name"));
    final String c_dayname = readString(map.get("day_name"));
    final String c_numberofcallsperday = readString(map.get("number_of_calls_per_day"));
    return new StatisticsCallsPerDayLast30Days(c_day, c_monthname, c_dayname, c_numberofcallsperday);
}

//number of sms per day in the last 30 days


@Override
public List <StatisticsSMSPerDayLast30Days> getSMSPerDayLast30Days (){
final SqlSession session = sessions.openSession();
try {
final List<Map<String, Object>> results = session.selectList(namespace + "Numberofsmsperdaylast30days");
     final List<StatisticsSMSPerDayLast30Days>  statistics = new ArrayList<StatisticsSMSPerDayLast30Days>();
     if (results != null && !results.isEmpty()) {
         for ( final Map<String, Object> result : results ) {
 statistics.add(toSMSPerDayLast30Days(result));
         }
     }

return statistics;
       } finally {
           session.close();
       }
}


private StatisticsSMSPerDayLast30Days toSMSPerDayLast30Days (final Map<String, Object> map) {

    final String c_day = readString(map.get("day"));
    final String c_monthname = readString(map.get("month_name"));
    final String c_dayname = readString(map.get("day_name"));
    final String c_numberofsmsperday = readString(map.get("number_of_sms_per_day"));



    return new StatisticsSMSPerDayLast30Days(c_day, c_monthname,c_dayname, c_numberofsmsperday);
}

//number of calls per month in the last 12 months


@Override
public List <StatisticsSMSPerMonthLast12Months> getSMSPerMonthLast12Months (){
final SqlSession session = sessions.openSession();
try {
final List<Map<String, Object>> results = session.selectList(namespace + "Numberofsmspermonthlast12months");
     final List<StatisticsSMSPerMonthLast12Months>  statistics = new ArrayList<StatisticsSMSPerMonthLast12Months>();
     if (results != null && !results.isEmpty()) {
         for ( final Map<String, Object> result : results ) {
 statistics.add(toSMSPerMonthLast12Months(result));
         }
     }

return statistics;
       } finally {
           session.close();
       }
}


private StatisticsSMSPerMonthLast12Months toSMSPerMonthLast12Months(final Map<String, Object> map) {

    final String c_month = readString(map.get("month"));
    final String c_monthname = readString(map.get("month_name"));
    final String c_year = readString(map.get("year"));
    final String c_numberofsmspermonth = readString(map.get("number_of_sms_per_month"));



    return new StatisticsSMSPerMonthLast12Months(c_month, c_monthname,c_year, c_numberofsmspermonth);
}



//number of sms per month in the last 12 months

@Override
public List <StatisticsCallsPerMonthLast12Months> getCallsPerMonthLast12Months (){
final SqlSession session = sessions.openSession();
try {
final List<Map<String, Object>> results = session.selectList(namespace + "Numberofcallspermonthlast12months");
     final List<StatisticsCallsPerMonthLast12Months>  statistics = new ArrayList<StatisticsCallsPerMonthLast12Months>();
     if (results != null && !results.isEmpty()) {
         for ( final Map<String, Object> result : results ) {
 statistics.add(toCallsPerMonthLast12Months(result));



         }
     }

return statistics;
       } finally {
           session.close();
       }
}


private StatisticsCallsPerMonthLast12Months toCallsPerMonthLast12Months(final Map<String, Object> map) {

    final String c_month = readString(map.get("month"));
    final String c_monthname = readString(map.get("month_name"));
    final String c_year = readString(map.get("year"));
    final String c_numberofcallspermonth = readString(map.get("number_of_calls_per_month"));



    return new StatisticsCallsPerMonthLast12Months(c_month, c_monthname,c_year, c_numberofcallspermonth);
}


}
