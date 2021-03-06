package net.apachegui.directives;

import net.apachegui.db.SettingsDao;
import net.apachegui.global.Constants;
import net.apachegui.modules.SharedModuleHandler;
import net.apachegui.modules.StaticModuleHandler;

import org.apache.log4j.Logger;

import apache.conf.parser.DirectiveParser;

public class MaxKeepAliveRequests extends GlobalSingletonDirective {
    private static Logger log = Logger.getLogger(MaxKeepAliveRequests.class);

    private int numberOfRequests;
    private final static int defaultNumberOfRequests = 100;

    public MaxKeepAliveRequests() {
        super(Constants.maxKeepAliveRequestsDirective);

        this.numberOfRequests = defaultNumberOfRequests;
    }

    public MaxKeepAliveRequests(int numberOfRequests) {
        super(Constants.maxKeepAliveRequestsDirective);

        this.numberOfRequests = numberOfRequests;
    }

    /**
     * Creates a MaxKeepAliveRequests object from the directive. The directive should conform to the following format:
     * 
     * MaxKeepAliveRequests numberOfRequests
     * 
     * @param directiveValue
     */
    public MaxKeepAliveRequests(String directiveValue) {
        super(Constants.maxKeepAliveRequestsDirective);

        directiveValue = directiveValue.trim().toLowerCase();

        try {
            this.numberOfRequests = Integer.parseInt(directiveValue);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public int getNumberOfRequests() {
        return numberOfRequests;
    }

    public void setNumberOfRequests(int numberOfRequests) {
        this.numberOfRequests = numberOfRequests;
    }

    /**
     * Static function to get the current configured MaxKeepAliveRequests in apache. If there is no MaxKeepAliveRequests found then a MaxKeepAliveRequests is returned with a value of 100
     * numberOfRequests.
     * 
     * @return a MaxKeepAliveRequests object, is no MaxKeepAliveRequests is found then a MaxKeepAliveRequests object is returned a value of 100 numberOfRequests.
     * @throws Exception
     */
    public static MaxKeepAliveRequests getMaxKeepAliveRequests() throws Exception {
        return (new MaxKeepAliveRequests().getGlobalConfigured());
    }

    /**
     * Function to get the current configured MaxKeepAliveRequests in apache. If there is no MaxKeepAliveRequests found then a MaxKeepAliveRequests is returned with a value of 100 numberOfRequests.
     * 
     * @return a MaxKeepAliveRequests object, is no MaxKeepAliveRequests is found then a MaxKeepAliveRequests object is returned a value of 100 numberOfRequests.
     * @throws Exception
     */
    @Override
    public MaxKeepAliveRequests getGlobalConfigured() throws Exception {
        String maxKeepAliveRequests[] = new DirectiveParser(SettingsDao.getInstance().getSetting(Constants.confFile), SettingsDao.getInstance().getSetting(Constants.serverRoot),
                StaticModuleHandler.getStaticModules(), SharedModuleHandler.getSharedModules()).getDirectiveValue(Constants.maxKeepAliveRequestsDirective, false);

        MaxKeepAliveRequests maxKeepAliveRequestsFound = null;

        if (maxKeepAliveRequests.length == 0) {
            maxKeepAliveRequestsFound = new MaxKeepAliveRequests(defaultNumberOfRequests);
        } else {
            maxKeepAliveRequestsFound = new MaxKeepAliveRequests(maxKeepAliveRequests[0].trim());
        }

        return maxKeepAliveRequestsFound;
    }

    @Override
    public String toString() {
        return Constants.maxKeepAliveRequestsDirective + " " + numberOfRequests;
    }
}
