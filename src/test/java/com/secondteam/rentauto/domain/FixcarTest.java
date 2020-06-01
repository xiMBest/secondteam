package com.secondteam.rentauto.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.secondteam.rentauto.web.rest.TestUtil;

public class FixcarTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fixcar.class);
        Fixcar fixcar1 = new Fixcar();
        fixcar1.setId(1L);
        Fixcar fixcar2 = new Fixcar();
        fixcar2.setId(fixcar1.getId());
        assertThat(fixcar1).isEqualTo(fixcar2);
        fixcar2.setId(2L);
        assertThat(fixcar1).isNotEqualTo(fixcar2);
        fixcar1.setId(null);
        assertThat(fixcar1).isNotEqualTo(fixcar2);
    }
}
