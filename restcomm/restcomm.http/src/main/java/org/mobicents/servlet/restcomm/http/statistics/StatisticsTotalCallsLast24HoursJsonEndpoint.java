package org.mobicents.servlet.restcomm.http.statistics;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.mobicents.servlet.restcomm.annotations.concurrency.ThreadSafe;



@Path("/Accounts/{accountSid}/Statistics/Totalcallslast24hours.json")
@ThreadSafe
public class StatisticsTotalCallsLast24HoursJsonEndpoint extends StatisticsTotalCallsLast24HoursEndpoint  {
            public StatisticsTotalCallsLast24HoursJsonEndpoint() {
                 super();
                }

//Application integration


@GET
public Response getTotalCallsLast24Hours(@PathParam("accountSid") final String accountSid ) {
return getTotalCallsLast24Hours(accountSid, APPLICATION_JSON_TYPE);
}


}
