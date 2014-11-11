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


/**
 * @author charles.roufay@telestax.com (Charles Roufay)
 */

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.configuration.Configuration;
import org.apache.shiro.authz.AuthorizationException;
import org.mobicents.servlet.restcomm.dao.AccountsDao;
import org.mobicents.servlet.restcomm.dao.DaoManager;
import org.mobicents.servlet.restcomm.dao.StatisticsDao;
import org.mobicents.servlet.restcomm.http.AbstractEndpoint;
import org.mobicents.servlet.restcomm.statistics.StatisticsTotalCallsLast24Hours;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class StatisticsTotalCallsLast24HoursEndpoint extends AbstractEndpoint{

@Context
   protected ServletContext context;
    protected Configuration configuration;
    protected StatisticsDao dao;
    protected Gson gson;
   // protected XStream xstream;
    protected AccountsDao accountsDao;

    public StatisticsTotalCallsLast24HoursEndpoint() {
        super();
    }



    @PostConstruct
    public void init() {
        final DaoManager storage = (DaoManager) context.getAttribute(DaoManager.class.getName());
        dao = storage.getStatisticsDao();
        accountsDao = storage.getAccountsDao();
        configuration = (Configuration) context.getAttribute(Configuration.class.getName());
        configuration = configuration.subset("runtime-settings");
        super.init(configuration);
        final StatisticsTotalCallsLast24HoursConverter converter = new StatisticsTotalCallsLast24HoursConverter(configuration);
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(StatisticsTotalCallsLast24Hours.class, converter);
        builder.setPrettyPrinting();
        gson = builder.create();

    }




 protected Response getTotalCallsLast24Hours(final String accountSid,final MediaType responseType) {
     try {
         secure(accountsDao.getAccount(accountSid), "RestComm:Read:Statistics");
     } catch (final AuthorizationException exception) {
         return status(UNAUTHORIZED).build();
     }
     final StatisticsTotalCallsLast24Hours statistics =    dao.getTotalCallsLast24Hours();
      if (APPLICATION_JSON_TYPE == responseType) {
         return ok(gson.toJson(statistics), APPLICATION_JSON).build();
     } else {
         return null;
     }
 }


}
