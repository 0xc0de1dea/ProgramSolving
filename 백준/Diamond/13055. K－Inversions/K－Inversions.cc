/**
 * Written by 0xc0de1dea
 * Email : 0xc0de1dea@gmail.com
 * FFT Reference : koosaga team note
 */

#include <bits/stdc++.h>

using namespace std;
using ll = long long;
using matrix = vector<vector<ll>>;
const double PI = acos(-1);
const int MOD = 99991;
typedef complex<double> cpx;

void fft(vector<cpx> &f, bool inv) {
    int n = f.size(), j = 0;

    vector<cpx> roots(n >> 1);
    for (int i = 1; i < n; i++){
        int bit = n >> 1;

        while (j >= bit) j -= bit, bit >>= 1;
        j += bit;

        if (i < j) swap(f[i], f[j]);
    }

    double angle = 2 * PI / n * (inv ? -1 : 1);
    for (int i = 0; i < n >> 1; i++) roots[i] = cpx(cos(angle * i), sin(angle * i));
    for (int i = 2; i <= n; i <<= 1){
        int step = n / i;

        for (int j = 0; j < n; j += i){
            for (int k = 0; k < i / 2; k++){
                cpx u = f[j + k], v = f[j + k + i / 2] * roots[step * k];
                f[j + k] = u + v;
                f[j + k + i / 2] = u - v;
            }
        }
    }

    if (inv) for (int i = 0; i < n; i++) f[i] /= n;
}

vector<int> mul(vector<int> &u, vector<int> &v) {
    vector<cpx> fu(u.begin(), u.end()), fv(v.begin(), v.end());

    int n = 2; while (n < u.size() + v.size()) n <<= 1;
    fu.resize(n); fv.resize(n);

    fft(fu, false); fft(fv, false);
    for (int i = 0; i < n; i++) fu[i] *= fv[i];
    fft(fu, true);

    vector<int> ret(n);
    for (int i = 0; i < n; i++) ret[i] = round(fu[i].real());

    return ret;
}

vector<int> pow(vector<int> base, int exp){
    vector<int> ret(base.size());
    if (exp & 1) ret = base, exp--;
    else ret[0] = 1;

    while (exp){
        if (exp & 1) ret = mul(ret, base);

        base = mul(base, base);
        exp >>= 1;
    }

    return ret;
}

void parse(string s, int n){
	int len = s.size() / n;
	int tmp = 0;
	int i = 0, j = 0;

	if (s.size() % n == 1) ++len;

	if (s.size() % n == 1){
		for (; i < s.size() % n; ++i) tmp = tmp * 10 + s[i] - '0';
		s[j++] = tmp;
	}

	while (i < s.size()){
		tmp = 0;
		for (int k = 0; k < n; ++k) tmp = tmp * 10 + s[i + k] - '0';
		i += n;
		s[j++] = tmp;
	}
}

matrix operator * (matrix &a, matrix &b){
    matrix c(2, vector<ll>(2));

    for (int i = 0; i < 2; i++){
        for (int j = 0; j < 2; j++){
            for (int k = 0; k < 2; k++){
                c[i][j] += a[i][k] * b[k][j];
            }
            c[i][j] %= MOD;
        }
    }

    return c;
}

matrix matrix_2x2_pow(matrix &m, ll exp){
    matrix ret = {{1, 0}, {0, 1}};

    while (exp){
        if (exp & 1) ret = ret * m;
        m = m * m; exp >>= 1;
    }

    return ret;
}

int get_fibo(int n){
    matrix base = {{1, 1}, {1, 0}};
    base = matrix_2x2_pow(base, n);
    return base[0][1];
}

signed main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL), cout.tie(NULL);

    string letter; cin >> letter;
    int sz = letter.size();
    vector<int> a, b, c;
    for (int i = 0; i < sz; i++){
        if (letter[i] == 'B') { a.push_back(1); b.push_back(0); }
        else { a.push_back(0); b.push_back(1); }
    }
    reverse(b.begin(), b.end());
    c = mul(a, b);

    for (int i = sz - 2; i >= 0; i--) cout << c[i] << '\n';
}