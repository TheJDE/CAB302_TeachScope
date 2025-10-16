package com.cab302.teachscope.models;

import com.cab302.teachscope.models.dao.DbFormDao;
import com.cab302.teachscope.models.dao.FormDao;
import com.cab302.teachscope.models.services.GenerateReportsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

public class ReportTest {
    private FormDao formDao;
    private GenerateReportsService generateReportsService;

    @BeforeEach
    void setup() {
        formDao = new DbFormDao();
        generateReportsService = new GenerateReportsService(formDao);
    }
}
