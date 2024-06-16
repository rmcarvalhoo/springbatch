package br.com.ricardo.chunkspringbatch.chunck;

import br.com.ricardo.chunkspringbatch.model.LineDTO;
import br.com.ricardo.chunkspringbatch.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@Slf4j
public class LinesWriter implements ItemWriter<LineDTO>, StepExecutionListener {

    private FileUtils fu;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        fu = new FileUtils("output_pepole.csv");
        log.info("Line Writer initialized.");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        fu.closeWriter();
        log.info("Line Writer ended.");
        return ExitStatus.COMPLETED;
    }

    @Override
    public void write(Chunk<? extends LineDTO> chunk) throws Exception {
        for (LineDTO line : chunk) {
            fu.writeLine(line);
            log.info("Wrote line " + line.toString());
        }
    }
}