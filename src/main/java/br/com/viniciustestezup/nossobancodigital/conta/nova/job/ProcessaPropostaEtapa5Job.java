package br.com.viniciustestezup.nossobancodigital.conta.nova.job;

import br.com.viniciustestezup.nossobancodigital.conta.nova.service.NovaPropostaEtapa5Service;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.UUID;


public class ProcessaPropostaEtapa5Job extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(ProcessaPropostaEtapa5Job.class);

    @Autowired
    private NovaPropostaEtapa5Service novaPropostaEtapa5Service;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Executando Job:: {}", jobExecutionContext.getJobDetail().getKey());

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

        UUID propostaId = UUID.fromString(jobDataMap.getString("propostaId"));
        logger.info(propostaId.toString());
        novaPropostaEtapa5Service.execute(propostaId);
    }
}
