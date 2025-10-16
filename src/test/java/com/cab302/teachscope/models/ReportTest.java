package com.cab302.teachscope.models;

import com.cab302.teachscope.models.dao.DbFormDao;
import com.cab302.teachscope.models.dao.DbStudentDao;
import com.cab302.teachscope.models.dao.FormDao;
import com.cab302.teachscope.models.dao.StudentDao;
import com.cab302.teachscope.models.services.GenerateReportsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

public class ReportTest {
    private FormDao formDao;
    private StudentDao studentDao;
    private GenerateReportsService generateReportsService;

    @BeforeEach
    void setup() {
        formDao = new DbFormDao();
        studentDao = new DbStudentDao();
        generateReportsService = new GenerateReportsService(formDao, studentDao);
    }
}
