package br.com.viniciustestezup.nossobancodigital.conta.transferencia.service;

import br.com.viniciustestezup.nossobancodigital.conta.transferencia.job.ProcessaTransacaoJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;

@Service
public class AdicionaNovoJobProcessaTransacaoService {

    private static final Logger logger = LoggerFactory.getLogger(AdicionaNovoJobProcessaTransacaoService.class);

    private final ZonedDateTime dateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("America/Sao_Paulo"));

    @Autowired
    private Scheduler scheduler;

    public AdicionaNovoJobProcessaTransacaoService() { }

    public void execute(Long id) {
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("Id", id.toString());
            JobDetail job = configJobDetail(jobDataMap);
            Trigger trigger = configTrigger(job);

            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.error("Falha ao executar o scheduler AdicionaNovoJobProcessaTransacaoService", e);
        }
    }

    private JobDetail configJobDetail(JobDataMap jobDataMap) {
        return newJob(ProcessaTransacaoJob.class)
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger configTrigger(JobDetail job) {
        return TriggerBuilder.newTrigger()
                .withIdentity(job.getKey().getName(), "Transferencia")
                .forJob(job)
                .withDescription("Processa Transferencia de valores")
                .startAt(Date.from(dateTime.toInstant().plusSeconds(3)))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
