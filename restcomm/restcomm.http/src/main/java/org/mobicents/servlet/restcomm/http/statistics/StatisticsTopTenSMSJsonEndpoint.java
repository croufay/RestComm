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

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.mobicents.servlet.restcomm.annotations.concurrency.ThreadSafe;



@Path("/Accounts/{accountSid}/Statistics/Toptensendersms.json")
@ThreadSafe
public class StatisticsTopTenSMSJsonEndpoint extends StatisticsTopTenSMSEndpoint  {
            public StatisticsTopTenSMSJsonEndpoint() {
                 super();
                }

//Application integration

@GET
public Response getStatistics(@PathParam("accountSid") final String accountSid) {
         return getStatistics(accountSid,  APPLICATION_JSON_TYPE);
}




}