package com.stackify.api.common.mask;


import com.stackify.api.common.util.PropertyUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

/**
 * Handled properties:
 * <p>
 * stackify.log.mask.enabled=true|false
 * stackify.log.mask.[CREDITCARD|SSN|IP]=true|false
 * stackify.log.mask.custom.[CUSTOM_LABEL]=[regex]
 *
 * @author Darin Howard
 */
@Slf4j
public class MaskerConfiguration {

    public static Masker fromProperties() {
        return fromProperties("/stackify-api.properties");
    }

    public static Masker fromProperties(String path) {

        Masker masker = new Masker();

        // set default enabled masks
        masker.addMask(Masker.MASK_CREDITCARD);
        masker.addMask(Masker.MASK_SSN);

        try {

            if (path != null) {

                Map<String, String> map = PropertyUtil.read(path);

                // masker defaults to disabled
                if (!map.containsKey("stackify.log.mask.enabled") ||
                        !Boolean.parseBoolean(map.get("stackify.log.mask.enabled")))  {
                    masker.clearMasks();
                    return masker;
                }

                // set masks
                for (String builtinMask : Masker.MASKS) {
                    if (map.containsKey("stackify.log.mask." + builtinMask)) {
                        if (Boolean.parseBoolean(map.get("stackify.log.mask." + builtinMask))) {
                            masker.addMask(builtinMask);
                        } else {
                            masker.removeMask(builtinMask);
                        }
                    }
                }

                // set custom masks
                for (Object key : map.keySet()) {
                    if (key.toString().startsWith("stackify.log.mask.custom.")) {
                        masker.addMask(map.get(key.toString()));
                    }
                }


            }

        } catch (Throwable t) {
            log.error("Exception reading " + path + " configuration file", t);
        }

        return masker;
    }

}
