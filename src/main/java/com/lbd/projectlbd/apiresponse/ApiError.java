package com.lbd.projectlbd.apiresponse;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)  // don't show null/empty values
public interface ApiError {
}
