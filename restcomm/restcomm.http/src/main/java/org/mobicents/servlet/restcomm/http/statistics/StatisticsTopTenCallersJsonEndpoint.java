package org.mobicents.servlet.restcomm.http.statistics;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.mobicents.servlet.restcomm.annotations.concurrency.ThreadSafe;



@Path("/Accounts/{accountSid}/Statistics/Toptencallers.json")
@ThreadSafe
public class StatisticsTopTenCallersJsonEndpoint extends StatisticsTopTenCallersEndpoint  {
            public StatisticsTopTenCallersJsonEndpoint() {
                 super();
                }

//Application integration

@GET
public Response getStatistics(@PathParam("accountSid") final String accountSid) {
         return getStatistics(accountSid,  APPLICATION_JSON_TYPE);
}




}