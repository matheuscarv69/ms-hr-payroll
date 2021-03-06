package src.configs.internalization

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import java.util.*

@Configuration

class InternalizationConfig {

    @Bean
    fun messageResource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()

        messageSource.setBasename("classpath:messages")
        messageSource.setDefaultEncoding("UTF-8")
        messageSource.setDefaultLocale(Locale.getDefault())

        return messageSource
    }

    @Bean
    fun validatorFactoryBean(): LocalValidatorFactoryBean {
        val localValidatorFactoryBean = LocalValidatorFactoryBean()
        localValidatorFactoryBean.setValidationMessageSource(messageResource())
        return localValidatorFactoryBean
    }
}