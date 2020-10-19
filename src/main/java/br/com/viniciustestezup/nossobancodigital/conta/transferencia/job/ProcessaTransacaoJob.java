package br.com.viniciustestezup.nossobancodigital.conta.transferencia.job;

import br.com.viniciustestezup.nossobancodigital.conta.transferencia.service.ProcessaTransacaoService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ProcessaTransacaoJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessaTransacaoJob.class);

    @Autowired
    private ProcessaTransacaoService processaTransacaoService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("Executando Job:: {}", jobExecutionContext.getJobDetail().getKey());

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

        Long id = Long.parseLong(jobDataMap.getString("Id"));
        LOGGER.info(String.format("Transação sendo processada:: Id = %s",id));
        processaTransacaoService.execute(id);
    }
}
