package org.newcih.wxapi.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 启动前检查
 *
 * @author NEWCIH
 */
@Component
public class WechatapiApplicationContextInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        /**
         * 打印Banner
         */
        System.out.println("                                                                     _                                              _  _     \n" +
                "                                                                    | |                                            (_)| |    \n" +
                "  ___   _ __    ___  _ __   ___   ___   _   _  _ __   ___   ___     | |__   _   _      _ __    ___ __      __  ___  _ | |__  \n" +
                " / _ \\ | '_ \\  / _ \\| '_ \\ / __| / _ \\ | | | || '__| / __| / _ \\    | '_ \\ | | | |    | '_ \\  / _ \\\\ \\ /\\ / / / __|| || '_ \\ \n" +
                "| (_) || |_) ||  __/| | | |\\__ \\| (_) || |_| || |   | (__ |  __/    | |_) || |_| |    | | | ||  __/ \\ V  V / | (__ | || | | |\n" +
                " \\___/ | .__/  \\___||_| |_||___/ \\___/  \\__,_||_|    \\___| \\___|    |_.__/  \\__, |    |_| |_| \\___|  \\_/\\_/   \\___||_||_| |_|\n" +
                "       | |                                                                   __/ |                                           \n" +
                "       |_|      :: write by newcih :: (v1.1.2.RELEASE)                      |___/                                            \n\n");

    }
}
