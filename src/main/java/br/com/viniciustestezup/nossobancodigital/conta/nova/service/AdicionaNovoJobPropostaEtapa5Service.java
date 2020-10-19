package br.com.viniciustestezup.nossobancodigital.conta.nova.service;

import br.com.viniciustestezup.nossobancodigital.conta.nova.job.ProcessaPropostaEtapa5Job;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import static org.quartz.JobBuilder.newJob;

@Service
public class AdicionaNovoJobPropostaEtapa5Service {

    private static final Logger logger = LoggerFactory.getLogger(AdicionaNovoJobPropostaEtapa5Service.class);

    private final ZonedDateTime dateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("America/Sao_Paulo"));

    @Autowired
    private Scheduler scheduler;

    public AdicionaNovoJobPropostaEtapa5Service() { }

    public void execute(UUID propostaId) {
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("propostaId", propostaId.toString());
            JobDetail job = configJobDetail(jobDataMap);
            Trigger trigger = configTrigger(job);

            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.error("Falha ao executar o scheduler AdicionaNovoJobPropostaEtapa5Service", e);
        }
    }

    private JobDetail configJobDetail(JobDataMap jobDataMap) {
        return newJob(ProcessaPropostaEtapa5Job.class)
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger configTrigger(JobDetail job) {
        return TriggerBuilder.newTrigger()
                .withIdentity(job.getKey().getName(), "Etapa-5")
                .forJob(job)
                .withDescription("Processa Proposta Etapa 5")
                .startAt(Date.from(dateTime.toInstant().plusSeconds(3)))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
