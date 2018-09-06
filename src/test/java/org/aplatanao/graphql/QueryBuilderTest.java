package org.aplatanao.graphql;

import org.junit.jupiter.api.Test;

public class QueryBuilderTest {

    @Test
    public void buildQuery() {
        new QueryBuilder("page")
                .arg("url", "http://news.ycombinator.com")
                //.field()
        ;
    }
}
