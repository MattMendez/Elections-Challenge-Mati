package net.avalith.elections.tasks;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class HistoricTask {

    @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
    public void addHistoric(){

    }
}
