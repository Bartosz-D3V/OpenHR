import { environment } from '../../environments/environment';

export class SystemVariables {
  public static readonly API_URL = environment.production ? '' : '/api';
  public static readonly TOKEN_PREFIX = 'openHR-TKN';
  public static readonly REFRESH_TOKEN_PREFIX = 'openHR-Refresh-TKN';
  public static readonly RETRY_TIMES = 2;
}
