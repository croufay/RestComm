/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.mobicents.servlet.sip.restcomm.http;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.mobicents.servlet.sip.restcomm.Account;
import org.mobicents.servlet.sip.restcomm.ServiceLocator;
import org.mobicents.servlet.sip.restcomm.Sid;
import org.mobicents.servlet.sip.restcomm.annotations.concurrency.ThreadSafe;
import org.mobicents.servlet.sip.restcomm.dao.AccountsDao;
import org.mobicents.servlet.sip.restcomm.dao.DaoManager;
import org.mobicents.servlet.sip.restcomm.http.converter.AccountConverter;

import com.thoughtworks.xstream.XStream;

/**
 * @author quintana.thomas@gmail.com (Thomas Quintana)
 */
@Path("/Accounts")
@ThreadSafe public final class AccountsEndpoint {
  private final DaoManager daos;
  
  public AccountsEndpoint() {
    super();
    final ServiceLocator services = ServiceLocator.getInstance();
    daos = services.get(DaoManager.class);
  }
  
  @Path("/{accountSid}")
  @GET public Response getAccount(@PathParam("accountSid") String accountSid) {
    final AccountsDao dao = daos.getAccountsDao();
    final Account account = dao.getAccount(new Sid(accountSid));
    final XStream xstream = new XStream();
    xstream.alias("Account", Account.class);
    xstream.registerConverter(new AccountConverter("2012-04-24"));
    return Response.ok(xstream.toXML(account), MediaType.APPLICATION_XML).build();
  }
}