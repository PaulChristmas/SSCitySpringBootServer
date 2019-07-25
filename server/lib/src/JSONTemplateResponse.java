package org.ss.server.lib;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class JSONTemplateResponse {

    @NonNull
    private boolean requestOk;
    @NonNull
    private String responseContent;
    private List<Object> recordList;

}