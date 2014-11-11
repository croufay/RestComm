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

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.configuration.Configuration;
import org.apache.shiro.authz.AuthorizationException;
import org.mobicents.servlet.restcomm.annotations.concurrency.NotThreadSafe;
import org.mobicents.servlet.restcomm.dao.AccountsDao;
import org.mobicents.servlet.restcomm.dao.DaoManager;
import org.mobicents.servlet.restcomm.dao.StatisticsDao;
import org.mobicents.servlet.restcomm.entities.Client;
import org.mobicents.servlet.restcomm.http.AbstractEndpoint;
import org.mobicents.servlet.restcomm.statistics.StatisticsCallsPerMonthLast12Months;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;



/**
 * @author charles.roufay@telestax.com (Charles Roufay)
 */
@NotThreadSafe

public abstract class StatisticsCallsPerMonthLast12MonthsEndpoint extends AbstractEndpoint{

@Context
    protected ServletContext context;
    protected Configuration configuration;
    protected StatisticsDao dao;
    protected Gson gson;
    protected XStream xstream;
    protected AccountsDao accountsDao;

    public StatisticsCallsPerMonthLast12MonthsEndpoint() {
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
        final StatisticsCallsPerMonthLast12MonthsConverter converter = new StatisticsCallsPerMonthLast12MonthsConverter(configuration);
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Client.class, converter);
        builder.setPrettyPrinting();
        gson = builder.create();

    }





 protected Response getStatistics(final String accountSid,  final MediaType responseType) {
        try {

            secure(accountsDao.getAccount(accountSid), "RestComm:Read:Statistics");
        } catch (final AuthorizationException exception) {
            return status(UNAUTHORIZED).build();
        }
        final List<StatisticsCallsPerMonthLast12Months> statistics =  dao.getCallsPerMonthLast12Months() ;
   if (APPLICATION_JSON_TYPE == responseType) {
            return ok(gson.toJson(statistics), APPLICATION_JSON).build();
        } else {
            return null;
        }
    }



}
