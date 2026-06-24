package core.network.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/** Tests parsing and validation for {@link ClientConnectionConfig}. */
public class ClientConnectionConfigTest {

  /** Empty dialog fields use the visible default values. */
  @Test
  void emptyFieldsUseDefaults() {
    ClientConnectionConfig config = ClientConnectionConfig.fromFields("", "");

    assertEquals("127.0.0.1", config.host());
    assertEquals(7777, config.port());
  }

  /** Host input is trimmed before validation. */
  @Test
  void hostIsTrimmed() {
    ClientConnectionConfig config = ClientConnectionConfig.fromFields("  example.org  ", "");

    assertEquals("example.org", config.host());
  }

  /** Local IPv4 addresses are accepted. */
  @Test
  void ipv4HostIsAccepted() {
    ClientConnectionConfig config = ClientConnectionConfig.fromFields("192.168.178.42", "");

    assertEquals("192.168.178.42", config.host());
  }

  /** IPv6 literals are accepted. */
  @Test
  void ipv6HostIsAccepted() {
    ClientConnectionConfig config = ClientConnectionConfig.fromFields("::1", "");

    assertEquals("::1", config.host());
  }

  /** CIDR masks are rejected because the client needs one concrete host. */
  @Test
  void hostWithNetworkMaskIsRejected() {
    assertThrows(
        IllegalArgumentException.class,
        () -> ClientConnectionConfig.fromFields("192.168.178.0/24", ""));
  }

  /** Mask placeholder characters are rejected before the network stack tries to resolve them. */
  @Test
  void hostWithMaskPlaceholderIsRejected() {
    assertThrows(
        IllegalArgumentException.class,
        () -> ClientConnectionConfig.fromFields("192.168.178.___", ""));
  }

  /** Malformed IPv4 addresses are rejected. */
  @Test
  void malformedIpv4HostIsRejected() {
    assertThrows(
        IllegalArgumentException.class, () -> ClientConnectionConfig.fromFields("192.168.178", ""));
  }

  /** Numeric-only hosts are rejected as malformed IPv4 input. */
  @Test
  void numericOnlyHostIsRejected() {
    assertThrows(
        IllegalArgumentException.class, () -> ClientConnectionConfig.fromFields("1231231231", ""));
  }

  /** IPv4 blocks above 255 are rejected. */
  @Test
  void ipv4BlockAboveMaximumIsRejected() {
    assertThrows(
        IllegalArgumentException.class,
        () -> ClientConnectionConfig.fromFields("192.168.178.999", ""));
  }

  /** A valid port input is parsed and used. */
  @Test
  void validPortIsUsed() {
    ClientConnectionConfig config = ClientConnectionConfig.fromFields("", " 12345 ");

    assertEquals(12345, config.port());
  }

  /** Empty host input uses the explicit default host. */
  @Test
  void emptyHostUsesExplicitDefaultHost() {
    ClientConnectionConfig config =
        ClientConnectionConfig.fromFields("", "12345", "example.org", 7777);

    assertEquals("example.org", config.host());
    assertEquals(12345, config.port());
  }

  /** Empty port input uses the explicit default port. */
  @Test
  void emptyPortUsesExplicitDefaultPort() {
    ClientConnectionConfig config =
        ClientConnectionConfig.fromFields("example.org", "", "localhost", 12345);

    assertEquals("example.org", config.host());
    assertEquals(12345, config.port());
  }

  /** Non-numeric port input is rejected. */
  @Test
  void nonNumericPortIsRejected() {
    assertThrows(
        IllegalArgumentException.class, () -> ClientConnectionConfig.fromFields("", "abc"));
  }

  /** Port zero is rejected. */
  @Test
  void portZeroIsRejected() {
    assertThrows(IllegalArgumentException.class, () -> ClientConnectionConfig.fromFields("", "0"));
  }

  /** Negative ports are rejected. */
  @Test
  void negativePortIsRejected() {
    assertThrows(IllegalArgumentException.class, () -> ClientConnectionConfig.fromFields("", "-1"));
  }

  /** Port values above 65535 are rejected. */
  @Test
  void portAboveMaximumIsRejected() {
    assertThrows(
        IllegalArgumentException.class, () -> ClientConnectionConfig.fromFields("", "65536"));
  }

  /** Empty host input is rejected when the configured default host is invalid. */
  @Test
  void emptyHostWithInvalidDefaultIsRejected() {
    assertThrows(
        IllegalArgumentException.class,
        () -> ClientConnectionConfig.fromFields("", "", "", ClientConnectionConfig.DEFAULT_PORT));
  }
}
