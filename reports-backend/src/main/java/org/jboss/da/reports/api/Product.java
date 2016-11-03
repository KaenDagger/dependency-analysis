package org.jboss.da.reports.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Honza Brázdil &lt;janinko.g@gmail.com&gt;
 */
@RequiredArgsConstructor
public class Product {

    @Getter
    @NonNull
    private String name;

    @Getter
    @NonNull
    private String version;

}
