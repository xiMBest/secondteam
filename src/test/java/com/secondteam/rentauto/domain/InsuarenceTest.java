package com.secondteam.rentauto.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.secondteam.rentauto.web.rest.TestUtil;

public class InsuarenceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Insuarence.class);
        Insuarence insuarence1 = new Insuarence();
        insuarence1.setId(1L);
        Insuarence insuarence2 = new Insuarence();
        insuarence2.setId(insuarence1.getId());
        assertThat(insuarence1).isEqualTo(insuarence2);
        insuarence2.setId(2L);
        assertThat(insuarence1).isNotEqualTo(insuarence2);
        insuarence1.setId(null);
        assertThat(insuarence1).isNotEqualTo(insuarence2);
    }
}
