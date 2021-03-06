package de.codecentric.spring.boot.chaos.monkey.watcher;

import de.codecentric.spring.boot.chaos.monkey.component.ChaosMonkey;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Benjamin Wilms
 */

@Aspect
public class SpringServiceAspect extends ChaosMonkeyBaseAspect{
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringServiceAspect.class);

    private final ChaosMonkey chaosMonkey;

    public SpringServiceAspect(ChaosMonkey chaosMonkey) {
        this.chaosMonkey = chaosMonkey;
    }

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void classAnnotatedWithControllerPointcut() {
    }

    @Pointcut("execution(* *.*(..))")
    public void allPublicMethodPointcut() {
    }

    @Around("classAnnotatedWithControllerPointcut() && allPublicMethodPointcut() && !classInChaosMonkeyPackage()")
    public Object intercept(ProceedingJoinPoint pjp) throws Throwable {
        LOGGER.debug(LOGGER.isDebugEnabled() ? "Controller class and public method detected: " + pjp.getSignature() : null);

        chaosMonkey.callChaosMonkey();

        return pjp.proceed();
    }

}
