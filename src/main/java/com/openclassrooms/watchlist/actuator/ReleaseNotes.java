package com.openclassrooms.watchlist.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id="release-notes")
public class ReleaseNotes {

    String version1 = "** Version 1.0 ** \n\n"
            + "* Homepage added \n"
            + "* Item creation form added \n"
            + "* Watchlsit page added \n";

    String version11 = "** Version 1.1 \n\n"
            + "* Reading from OMDb API added \n"
            + "* Actuator endpoints added \n";

    @ReadOperation
    public String releaseNotes() {
        return version1 + version11;
    }

    @ReadOperation
    public String selectReleaseNotes(@Selector String selector ) {
        if ("1.0".equals(selector)) return version1;
        else if ("1.1".equals(selector)) return version11;
        else return releaseNotes();
    }

}
