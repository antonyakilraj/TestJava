import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LdapConnection {


	static DirContext ldapContext;
	public static void main (String[] args) throws NamingException
	{
		try
		{
			System.out.println("test Active Directory");
			final String accountName = "user.1";

			Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);
			ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			ldapEnv.put(Context.PROVIDER_URL,  "ldap://localhost:389");
			ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
			ldapEnv.put(Context.SECURITY_PRINCIPAL, "cn=Directory Manager");
			ldapEnv.put(Context.SECURITY_CREDENTIALS, "Capgemini#123"); 
			ldapContext = new InitialDirContext(ldapEnv);

			SearchControls searchCtls = new SearchControls();
			String returnedAtts[]={"sn","givenName"};
			searchCtls.setReturningAttributes(returnedAtts);
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String searchFilter = "(&(objectClass=inetOrgPerson)(uid=" + accountName + "))";
			String searchBase = "dc=example,dc=com";

			int totalResults = 0;
			NamingEnumeration<SearchResult> answer = ldapContext.search(searchBase, searchFilter, searchCtls);
			
			while (answer.hasMoreElements())
			{
				SearchResult sr = (SearchResult)answer.next();

				totalResults++;

				System.out.println(sr.getName());
				javax.naming.directory.Attributes attrs = sr.getAttributes();
				System.out.println(attrs.get("givenName"));
				System.out.println(attrs.get("sn"));
			}

			System.out.println("Total results: " + totalResults);
			ldapContext.close();
		}
		catch (Exception e)
		{
			System.out.println(" Search error: " + e);
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
