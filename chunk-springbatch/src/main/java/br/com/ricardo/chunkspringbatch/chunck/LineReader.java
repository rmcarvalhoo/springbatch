package br.com.ricardo.chunkspringbatch.chunck;

import br.com.ricardo.chunkspringbatch.model.LineDTO;
import br.com.ricardo.chunkspringbatch.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;

@Slf4j
public class LineReader implements ItemReader<LineDTO>, StepExecutionListener {

    private FileUtils fu;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        fu = new FileUtils("input/people.csv");
        log.info("Line Reader initialized.");
    }

    @Override
    public LineDTO read() throws Exception {
        LineDTO line = fu.readLine();
        if (line != null) {
            log.info("Read line: " + line.toString());
        }
        return line;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        fu.closeReader();
        log.info("Line Reader ended.");
        return ExitStatus.COMPLETED;
    }
}