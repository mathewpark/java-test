package enumTest;

import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

public class EnumTestMain {
	public static void main(String[] args) {
		
	}
	
	public static class PrefixedIdTransferer {
		public static int parseId(String prefixedId, String PREFIX) throws Exception {
			try {
				if (prefixedId.indexOf(PREFIX) < 0) {
					throw new Exception();
				}
				return Integer.parseInt(prefixedId.substring(prefixedId.indexOf(PREFIX) + PREFIX.length()));
			} catch (Exception e) {
				throw new Exception();
			}
		}

		public static boolean parseBoolean(String prefixedId, String PREFIX) throws Exception {
			try {
				return prefixedId.contains(PREFIX);
			} catch (Exception e) {
				throw new Exception();
			}
		}
	}
	
	public static enum Resource {
		Zone("zone_"), Node("node_"), Instance("vm_"), PublicIp("pubIp_"), PrivateIp("privIp_"), FirewallGroup(
				"fg_"), Qos("qos_");

		private String PREFIX;

		Resource(String prefix) {
			this.PREFIX = prefix;
		}

		public JSONObject getJson(int id) throws JSONException {
			JSONObject root = new JSONObject();

			root.put("id", this.prefixId(id));

			return root;
		}

		public String prefixId(int id) {
			return String.format("%s%d", PREFIX, id);
		}

		public int parseId(String prefixedId) {
			try {
				return PrefixedIdTransferer.parseId(prefixedId, PREFIX);
			} catch (Exception e) {
				return -1;
			}
		}

		public boolean isParsableId(String prefixedId) {
			try {
				if (prefixedId.substring(0, PREFIX.length()).equals(PREFIX)
						&& Pattern.compile(String.format("%s%s", PREFIX, "[0-9]")).matcher(prefixedId).find()) {
					return true;
				}
				return false;
			} catch (Exception e) {
				return false;
			}
		}
	}
}
