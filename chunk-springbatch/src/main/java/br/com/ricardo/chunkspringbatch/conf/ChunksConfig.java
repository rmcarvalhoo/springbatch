package br.com.ricardo.chunkspringbatch.conf;

import br.com.ricardo.chunkspringbatch.chunck.LineProcessor;
import br.com.ricardo.chunkspringbatch.chunck.LineReader;
import br.com.ricardo.chunkspringbatch.chunck.LinesWriter;
import br.com.ricardo.chunkspringbatch.model.LineDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
//@EnableBatchProcessing
public class ChunksConfig {

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("chunksJob", jobRepository)
                .start(processLines(jobRepository, transactionManager, itemReader(), itemProcessor(), itemWriter()))
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    protected Step processLines(JobRepository jobRepository, PlatformTransactionManager transactionManager, ItemReader<LineDTO> reader,
                                ItemProcessor<LineDTO, LineDTO> processor, ItemWriter<LineDTO> writer) {
        return new StepBuilder("processLines", jobRepository).<LineDTO, LineDTO> chunk(2, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<LineDTO> itemReader() {
        return new LineReader();
    }

    @Bean
    public ItemProcessor<LineDTO, LineDTO> itemProcessor() {
        return new LineProcessor();
    }

    @Bean
    public ItemWriter<LineDTO> itemWriter() {
        return new LinesWriter();
    }

}
