// TODO https://github.com/jsdom/jsdom/issues/2524
// https://github.com/dtorannpu/todo-app/issues/2
import { TextDecoder, TextEncoder } from 'util';

(global as any).TextEncoder = TextEncoder;
(global as any).TextDecoder = TextDecoder;
