package com.segment.analytics.integrations;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.robolectric.annotation.Config.NONE;

import com.google.common.collect.ImmutableMap;
import com.segment.analytics.core.BuildConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

public class IdentifyPayloadTest {

  @Test
  public void invalidTraitsThrows() {
    try {
      //noinspection CheckResult,ConstantConditions
      new IdentifyPayload.Builder().traits(null);
      Assert.fail();
    } catch (NullPointerException e) {
      assertThat(e).hasMessage("traits == null");
    }
  }

  @Test
  public void traits() {
    IdentifyPayload payload = new IdentifyPayload.Builder()
        .userId("user_id")
        .traits(ImmutableMap.of("foo", "bar"))
        .build();
    assertThat(payload.traits()).isEqualTo(ImmutableMap.of("foo", "bar"));
    assertThat(payload).containsEntry(IdentifyPayload.TRAITS_KEY, ImmutableMap.of("foo", "bar"));
  }

  @Test
  public void requiresUserIdOrTraits() {
    try {
      //noinspection CheckResult
      new IdentifyPayload.Builder().anonymousId("anonymous_id").build();
      fail();
    } catch (NullPointerException e) {
      assertThat(e).hasMessage("either userId or traits are required");
    }
  }
}