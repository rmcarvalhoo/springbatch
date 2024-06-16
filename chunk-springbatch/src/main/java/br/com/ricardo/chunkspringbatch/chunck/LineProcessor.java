package br.com.ricardo.chunkspringbatch.chunck;

import br.com.ricardo.chunkspringbatch.model.LineDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Slf4j
public class LineProcessor implements ItemProcessor<LineDTO, LineDTO>, StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Line Processor initialized.");
    }

    @Override
    public LineDTO process(LineDTO line) throws Exception {
        long age = ChronoUnit.YEARS
                .between(line.getDob(), LocalDate.now());
        log.info("Calculated age " + age + " for line " + line.toString());
        line.setAge(age);
        return line;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Line Processor ended.");
        return ExitStatus.COMPLETED;
    }
}