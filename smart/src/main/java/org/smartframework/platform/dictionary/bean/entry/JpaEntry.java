package org.smartframework.platform.dictionary.bean.entry;

public class JpaEntry implements Comparable<JpaEntry> {
	private String queryName;
	private String keyName;
	private String valueName;
	private String emf;

	public JpaEntry(String qn, String kn, String vn, Object emf) {
		this.queryName = qn;
		this.keyName = kn;
		this.valueName = vn;
		if (emf != null) {
			this.emf = ((String) emf);
		}
	}

	public int compareTo(JpaEntry o) {
		return (this.queryName + this.keyName + this.valueName).compareTo(o.queryName + o.keyName + o.valueName);
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + (this.keyName == null ? 0 : this.keyName.hashCode());
		result = prime * result + (this.queryName == null ? 0 : this.queryName.hashCode());
		result = prime * result + (this.valueName == null ? 0 : this.valueName.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		JpaEntry other = (JpaEntry) obj;
		if (this.keyName == null) {
			if (other.keyName != null) {
				return false;
			}
		} else if (!this.keyName.equals(other.keyName)) {
			return false;
		}
		if (this.queryName == null) {
			if (other.queryName != null) {
				return false;
			}
		} else if (!this.queryName.equals(other.queryName)) {
			return false;
		}
		if (this.valueName == null) {
			if (other.valueName != null) {
				return false;
			}
		} else if (!this.valueName.equals(other.valueName)) {
			return false;
		}
		return true;
	}
	
	public String getQueryName() {
		return this.queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public String getKeyName() {
		return this.keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getValueName() {
		return this.valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getEmf() {
		return this.emf;
	}

	public void setEmf(String emf) {
		this.emf = emf;
	}
}
